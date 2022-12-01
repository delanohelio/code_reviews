[CompilationUnitImpl][CtPackageDeclarationImpl]package gov.cms.bfd.server.war.stu3.providers;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import javax.persistence.criteria.CriteriaQuery;
[CtUnresolvedImport]import javax.persistence.criteria.Join;
[CtImportImpl]import java.time.Instant;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import org.apache.spark.util.sketch.BloomFilter;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import javax.persistence.EntityManager;
[CtUnresolvedImport]import javax.persistence.PersistenceContext;
[CtUnresolvedImport]import org.springframework.scheduling.annotation.Scheduled;
[CtImportImpl]import org.slf4j.Logger;
[CtImportImpl]import java.util.Date;
[CtImportImpl]import java.util.function.Function;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.LoadedBatch;
[CtImportImpl]import javax.annotation.PostConstruct;
[CtUnresolvedImport]import org.springframework.stereotype.Component;
[CtUnresolvedImport]import ca.uhn.fhir.rest.param.DateRangeParam;
[CtUnresolvedImport]import gov.cms.bfd.model.rif.LoadedFile;
[CtUnresolvedImport]import javax.persistence.criteria.CriteriaBuilder;
[CtImportImpl]import java.util.List;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import javax.persistence.criteria.Root;
[CtClassImpl][CtJavaDocImpl]/**
 * Monitors the loaded files and their associated batches in the database. Creates Bloom filters to
 * match these files.
 */
[CtAnnotationImpl]@org.springframework.stereotype.Component
public class LoadedFilterManager {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger LOGGER = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.class);

    [CtFieldImpl][CtCommentImpl]// A date before the lastUpdate feature was rolled out
    private static final [CtTypeReferenceImpl]java.util.Date BEFORE_LAST_UPDATED_FEATURE = [CtInvocationImpl][CtTypeAccessImpl]java.util.Date.from([CtInvocationImpl][CtTypeAccessImpl]java.time.Instant.parse([CtLiteralImpl]"2020-01-01T00:00:00Z"));

    [CtFieldImpl][CtCommentImpl]// The size of the beneficiaryId column
    private static final [CtTypeReferenceImpl]int BENE_ID_SIZE = [CtLiteralImpl]15;

    [CtFieldImpl][CtCommentImpl]// The connection to the DB
    private [CtTypeReferenceImpl]javax.persistence.EntityManager entityManager;

    [CtFieldImpl][CtCommentImpl]// The filter set
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFileFilter> filters;

    [CtFieldImpl][CtCommentImpl]// The latest transaction time from the LoadedBatch files
    private [CtTypeReferenceImpl]java.util.Date transactionTime;

    [CtFieldImpl][CtCommentImpl]// The last LoadedBatch.created in the filter set
    private [CtTypeReferenceImpl]java.util.Date lastBatchCreated;

    [CtFieldImpl][CtCommentImpl]// The first LoadedBatch.created in the filter set
    private [CtTypeReferenceImpl]java.util.Date firstBatchCreated;

    [CtClassImpl][CtJavaDocImpl]/**
     * A tuple of values: LoadedFile.loadedFileid, LoadedFile.created, max(LoadedBatch.created). Used
     * for an optimized query that includes only what is needed to refresh filters
     */
    public static class LoadedTuple {
        [CtFieldImpl]private [CtTypeReferenceImpl]long loadedFileId;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Date firstUpdated;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Date lastUpdated;

        [CtConstructorImpl]public LoadedTuple([CtParameterImpl][CtTypeReferenceImpl]long loadedFileId, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date firstUpdated, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date lastUpdated) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.loadedFileId = [CtVariableReadImpl]loadedFileId;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.firstUpdated = [CtVariableReadImpl]firstUpdated;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.lastUpdated = [CtVariableReadImpl]lastUpdated;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]long getLoadedFileId() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]loadedFileId;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Date getFirstUpdated() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]firstUpdated;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Date getLastUpdated() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]lastUpdated;
        }
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Create a manager for {@link LoadedFileFilter}s.
     */
    public LoadedFilterManager() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.filters = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the list of current filters. Newest first.
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFileFilter> getFilters() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]filters;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The last time that the filter manager knows that database has been updated.
     *
     * @return the last batch's created timestamp
     */
    public [CtTypeReferenceImpl]java.util.Date getTransactionTime() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]transactionTime == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"LoadedFilterManager has not been initialized.");
        }
        [CtReturnImpl]return [CtFieldReadImpl]transactionTime;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The return the first batch that the filter manager knows about
     *
     * @return the first batch's created timestamp
     */
    public [CtTypeReferenceImpl]java.util.Date getLastBatchCreated() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]lastBatchCreated == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"LoadedFilterManager has not been refreshed.");
        }
        [CtReturnImpl]return [CtFieldReadImpl]lastBatchCreated;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * The return the first batch that the filter manager knows about
     *
     * @return the first batch's created timestamp
     */
    public [CtTypeReferenceImpl]java.util.Date getFirstBatchCreated() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]firstBatchCreated == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtLiteralImpl]"LoadedFilterManager has not been refreshed.");
        }
        [CtReturnImpl]return [CtFieldReadImpl]firstBatchCreated;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Setup the JPA entityManager for the database to query
     *
     * @param entityManager
     * 		to use
     */
    [CtAnnotationImpl]@javax.persistence.PersistenceContext
    public [CtTypeReferenceImpl]void setEntityManager([CtParameterImpl][CtTypeReferenceImpl]javax.persistence.EntityManager entityManager) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.entityManager = [CtVariableReadImpl]entityManager;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Called to finish initialization of the manager
     */
    [CtAnnotationImpl]@javax.annotation.PostConstruct
    public synchronized [CtTypeReferenceImpl]void init() [CtBlockImpl]{
        [CtAssignmentImpl][CtCommentImpl]// The transaction time will either the last LoadedBatch or some earlier time
        [CtFieldWriteImpl]transactionTime = [CtInvocationImpl][CtInvocationImpl]fetchLastLoadedBatchCreated().orElse([CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.BEFORE_LAST_UPDATED_FEATURE);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Is the result set going to be empty for this beneficiary and time period?
     *
     * <p>This result is eventually consistent with the state of the BFD database. The FilterManager's
     * knowledge of the state of the database lags the writes to the database by as much as a second.
     *
     * @param beneficiaryId
     * 		to test
     * @param lastUpdatedRange
     * 		to test
     * @return true if the results set is empty. false if the result set *may* contain items.
     */
    public synchronized [CtTypeReferenceImpl]boolean isResultSetEmpty([CtParameterImpl][CtTypeReferenceImpl]java.lang.String beneficiaryId, [CtParameterImpl][CtTypeReferenceImpl]ca.uhn.fhir.rest.param.DateRangeParam lastUpdatedRange) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]beneficiaryId == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]beneficiaryId.isEmpty())[CtBlockImpl]
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException();

        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]isInBounds([CtVariableReadImpl]lastUpdatedRange)) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// Out of bounds has to be treated as unknown result
            return [CtLiteralImpl]false;
        }
        [CtForEachImpl][CtCommentImpl]// Within the known interval that search for matching filters
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFileFilter filter : [CtFieldReadImpl]filters) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]filter.matchesDateRange([CtVariableReadImpl]lastUpdatedRange)) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]filter.mightContain([CtVariableReadImpl]beneficiaryId)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtLiteralImpl]false;
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]filter.getLastUpdated().getTime() < [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]lastUpdatedRange.getLowerBoundAsInstant().getTime()) [CtBlockImpl]{
                [CtReturnImpl][CtCommentImpl]// filters are sorted in descending by lastUpdated time, so we can exit early from this
                [CtCommentImpl]// loop
                return [CtLiteralImpl]true;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Test the passed in range against the range of information that filter manager knows about.
     *
     * <p>This result is eventually consistent with the state of the BFD database. The FilterManager's
     * knowledge of the state of the database lags the writes to the database by as much as a second.
     *
     * @param range
     * 		to test against
     * @return true iff the range is within the bounds of the filters
     */
    public synchronized [CtTypeReferenceImpl]boolean isInBounds([CtParameterImpl][CtTypeReferenceImpl]ca.uhn.fhir.rest.param.DateRangeParam range) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]range == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getFilters().size() == [CtLiteralImpl]0))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtCommentImpl]// The manager has a "known" interval which it has information about. The known range
        [CtCommentImpl]// is from the firstFilterUpdate to the future.
        final [CtTypeReferenceImpl]java.util.Date lowerBound = [CtInvocationImpl][CtVariableReadImpl]range.getLowerBoundAsInstant();
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]lowerBound != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]lowerBound.getTime() >= [CtInvocationImpl][CtInvocationImpl]getFirstBatchCreated().getTime());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Called periodically to build and refresh the filters list from the entityManager.
     *
     * <p>The {@link #lastBatchCreated} and {@link #firstBatchCreated} fields are updated by this
     * call.
     */
    [CtAnnotationImpl]@org.springframework.scheduling.annotation.Scheduled(fixedDelay = [CtLiteralImpl]1000, initialDelay = [CtLiteralImpl]2000)
    public synchronized [CtTypeReferenceImpl]void refreshFilters() [CtBlockImpl]{
        [CtTryImpl][CtCommentImpl]/* Dev note: the pipeline has a process to trim the files list. Nevertheless, building a set of
        bloom filters may take a while. This method is expected to be called on it's own thread, so
        this filter building process can happen without interfering with serving. Also, this refresh
        time will be proportional to the number of files which have been loaded in the past refresh
        period. If no files have been loaded, this refresh should take less than a millisecond.
         */
        try [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// If new batches are present, then build new filters for the affected files
            final [CtTypeReferenceImpl]java.util.Date currentLastBatchCreated = [CtInvocationImpl][CtInvocationImpl]fetchLastLoadedBatchCreated().orElse([CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.BEFORE_LAST_UPDATED_FEATURE);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtThisAccessImpl]this.lastBatchCreated == [CtLiteralImpl]null) || [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.lastBatchCreated.before([CtVariableReadImpl]currentLastBatchCreated)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.LOGGER.info([CtLiteralImpl]"Refreshing LoadedFile filters with new filters from {} to {}", [CtFieldReadImpl]lastBatchCreated, [CtVariableReadImpl]currentLastBatchCreated);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.LoadedTuple> loadedTuples = [CtInvocationImpl]fetchLoadedTuples([CtFieldReadImpl][CtThisAccessImpl]this.lastBatchCreated);
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.filters = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.updateFilters([CtFieldReadImpl][CtThisAccessImpl]this.filters, [CtVariableReadImpl]loadedTuples, [CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::fetchLoadedBatches);
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.lastBatchCreated = [CtVariableReadImpl]currentLastBatchCreated;
                [CtLocalVariableImpl][CtCommentImpl]// If batches been trimmed, then remove filters which are no longer present
                final [CtTypeReferenceImpl]java.util.Date currentFirstBatchUpdate = [CtInvocationImpl][CtInvocationImpl]fetchFirstLoadedBatchCreated().orElse([CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.BEFORE_LAST_UPDATED_FEATURE);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtThisAccessImpl]this.firstBatchCreated == [CtLiteralImpl]null) || [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.firstBatchCreated.before([CtVariableReadImpl]currentFirstBatchUpdate)) [CtBlockImpl]{
                    [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.LOGGER.info([CtLiteralImpl]"Trimmed LoadedFile filters before {}", [CtVariableReadImpl]currentFirstBatchUpdate);
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.model.rif.LoadedFile> loadedFiles = [CtInvocationImpl]fetchLoadedFiles();
                    [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.filters = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.trimFilters([CtFieldReadImpl][CtThisAccessImpl]this.filters, [CtVariableReadImpl]loadedFiles);
                    [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.firstBatchCreated = [CtVariableReadImpl]currentFirstBatchUpdate;
                }
                [CtAssignmentImpl][CtCommentImpl]// update the transaction time as well
                [CtFieldWriteImpl][CtThisAccessImpl]this.transactionTime = [CtVariableReadImpl]currentLastBatchCreated;
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.LOGGER.error([CtLiteralImpl]"Error found refreshing LoadedFile filters", [CtVariableReadImpl]ex);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the current state. Used in tests.
     *
     * @param filters
     * 		to use
     * @param firstBatchCreated
     * 		to use
     * @param lastBatchCreated
     * 		to use
     */
    public [CtTypeReferenceImpl]void set([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFileFilter> filters, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date firstBatchCreated, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date lastBatchCreated) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.filters = [CtVariableReadImpl]filters;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.firstBatchCreated = [CtVariableReadImpl]firstBatchCreated;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.lastBatchCreated = [CtVariableReadImpl]lastBatchCreated;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.transactionTime = [CtVariableReadImpl]lastBatchCreated;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return a info about the filter manager state
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"LoadedFilterManager [filters.size=" + [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl]filters != [CtLiteralImpl]null ? [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtInvocationImpl][CtFieldReadImpl]filters.size()) : [CtLiteralImpl]"null")) + [CtLiteralImpl]", transactionTime=") + [CtFieldReadImpl]transactionTime) + [CtLiteralImpl]", firstBatchCreated=") + [CtFieldReadImpl]firstBatchCreated) + [CtLiteralImpl]", lastBatchCreated=") + [CtFieldReadImpl]lastBatchCreated) + [CtLiteralImpl]"]";
    }

    [CtMethodImpl][CtCommentImpl]/* Dev Note: The following static methods encapsulate the logic of the manager. They are separated
    from the state of the manager to allow for easy testing. They should be considered private to
    the class, but they are made public for tests.

    <p>If you are interested, the idea comes from:
    https://www.mokacoding.com/blog/functional-core-reactive-shell/
     */
    [CtJavaDocImpl]/**
     * Create an updated {@link LoadedFileFilter} list from existing filters and newly loaded files
     * and batches
     *
     * @param existingFilters
     * 		that should be included
     * @param loadedTuples
     * 		that come from new LoadedBatch
     * @param fetchById
     * 		to use retrieve list of LoadedBatch by id
     * @return a new filter list
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFileFilter> updateFilters([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFileFilter> existingFilters, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.LoadedTuple> loadedTuples, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]java.lang.Long, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.model.rif.LoadedBatch>> fetchById) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFileFilter> result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]existingFilters);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFileFilter> newFilters = [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.buildFilters([CtVariableReadImpl]loadedTuples, [CtVariableReadImpl]fetchById);
        [CtInvocationImpl][CtVariableReadImpl]newFilters.forEach([CtLambdaImpl]([CtParameterImpl] filter) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]result.removeIf([CtLambdaImpl]([CtParameterImpl] f) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]f.getLoadedFileId() == [CtInvocationImpl][CtVariableReadImpl]filter.getLoadedFileId());
            [CtInvocationImpl][CtVariableReadImpl]result.add([CtVariableReadImpl]filter);
        });
        [CtInvocationImpl][CtVariableReadImpl]result.sort([CtLambdaImpl]([CtParameterImpl] a,[CtParameterImpl] b) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]b.getFirstUpdated().compareTo([CtInvocationImpl][CtVariableReadImpl]a.getFirstUpdated()));[CtCommentImpl]// Descending

        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Build a new {@link LoadedFileFilter} list
     *
     * @param loadedTuples
     * 		that come from new LoadedBatch
     * @param fetchById
     * 		to use retrieve list of LoadedBatch by id
     * @return a new filter list
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFileFilter> buildFilters([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.LoadedTuple> loadedTuples, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]java.lang.Long, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.model.rif.LoadedBatch>> fetchById) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]loadedTuples.stream().map([CtLambdaImpl]([CtParameterImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.LoadedTuple t) -> [CtInvocationImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.buildFilter([CtInvocationImpl][CtVariableReadImpl]t.getLoadedFileId(), [CtInvocationImpl][CtVariableReadImpl]t.getFirstUpdated(), [CtVariableReadImpl]fetchById)).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Trim filters to match current {@link LoadedFile} list. Only deletes filters.
     *
     * @param existingFilters
     * 		to reuse if possible
     * @param loadedFiles
     * 		list of current loaded files
     * @return a new filter list
     */
    public static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFileFilter> trimFilters([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFileFilter> existingFilters, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.model.rif.LoadedFile> loadedFiles) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFileFilter> newFilters = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]existingFilters);
        [CtInvocationImpl][CtVariableReadImpl]newFilters.removeIf([CtLambdaImpl]([CtParameterImpl] filter) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]loadedFiles.stream().noneMatch([CtLambdaImpl]([CtParameterImpl] f) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]f.getLoadedFileId() == [CtInvocationImpl][CtVariableReadImpl]filter.getLoadedFileId()));
        [CtReturnImpl]return [CtVariableReadImpl]newFilters;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Build a filter for this loaded file. Should be called in a synchronized context.
     *
     * @param fileId
     * 		to build a filter for
     * @param firstUpdated
     * 		time stamp
     * @param fetchById
     * 		a function which returns a list of batches
     * @return a new filter
     */
    public static [CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFileFilter buildFilter([CtParameterImpl][CtTypeReferenceImpl]long fileId, [CtParameterImpl][CtTypeReferenceImpl]java.util.Date firstUpdated, [CtParameterImpl][CtTypeReferenceImpl]java.util.function.Function<[CtTypeReferenceImpl]java.lang.Long, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.model.rif.LoadedBatch>> fetchById) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.model.rif.LoadedBatch> loadedBatches = [CtInvocationImpl][CtVariableReadImpl]fetchById.apply([CtVariableReadImpl]fileId);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]int batchCount = [CtInvocationImpl][CtVariableReadImpl]loadedBatches.size();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]batchCount == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Batches cannot be empty for a filter");
        }
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]int batchSize = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]loadedBatches.get([CtLiteralImpl]0).getBeneficiaries().length() + [CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.BENE_ID_SIZE) / [CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.BENE_ID_SIZE;
        [CtLocalVariableImpl][CtCommentImpl]// It is important to get a good estimate of the number of entries for
        [CtCommentImpl]// an accurate FFP and minimal memory size. This one assumes that all batches are of equal size.
        final [CtTypeReferenceImpl]org.apache.spark.util.sketch.BloomFilter bloomFilter = [CtInvocationImpl][CtTypeAccessImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFileFilter.createFilter([CtBinaryOperatorImpl][CtVariableReadImpl]batchSize * [CtVariableReadImpl]batchCount);
        [CtLocalVariableImpl][CtCommentImpl]// Loop through all batches, filling the bloom filter and finding the lastUpdated
        [CtTypeReferenceImpl]java.util.Date lastUpdated = [CtVariableReadImpl]firstUpdated;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]gov.cms.bfd.model.rif.LoadedBatch batch : [CtVariableReadImpl]loadedBatches) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String beneficiary : [CtInvocationImpl][CtVariableReadImpl]batch.getBeneficiariesAsList()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]bloomFilter.putString([CtVariableReadImpl]beneficiary);
            }
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]batch.getCreated().after([CtVariableReadImpl]lastUpdated)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]lastUpdated = [CtInvocationImpl][CtVariableReadImpl]batch.getCreated();
            }
        }
        [CtInvocationImpl][CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.LOGGER.info([CtLiteralImpl]"Built a filter for {} with {} batches", [CtVariableReadImpl]fileId, [CtInvocationImpl][CtVariableReadImpl]loadedBatches.size());
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFileFilter([CtVariableReadImpl]fileId, [CtVariableReadImpl]batchCount, [CtVariableReadImpl]firstUpdated, [CtVariableReadImpl]lastUpdated, [CtVariableReadImpl]bloomFilter);
    }

    [CtMethodImpl][CtCommentImpl]/* DB Operations */
    [CtJavaDocImpl]/**
     * Return the max date from the LoadedBatch table. If no batches are present, then the schema
     * migration time which will be a timestamp before the first loaded batch
     *
     * @return the max date
     */
    private [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.util.Date> fetchLastLoadedBatchCreated() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date maxCreated = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]entityManager.createQuery([CtLiteralImpl]"select max(b.created) from LoadedBatch b", [CtFieldReadImpl]java.util.Date.class).getSingleResult();
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtVariableReadImpl]maxCreated);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return the min date from the LoadedBatch table
     *
     * @return the min date
     */
    private [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]java.util.Date> fetchFirstLoadedBatchCreated() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date minBatchId = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]entityManager.createQuery([CtLiteralImpl]"select min(b.created) from LoadedBatch b", [CtFieldReadImpl]java.util.Date.class).getSingleResult();
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtVariableReadImpl]minBatchId);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Fetch the tuple of (loadedFileId, LoadedFile.created, max(LoadedBatch.created))
     *
     * @param after
     * 		limits the query to include batches created after this timestamp
     * @return tuples that meet the after criteria or an empty list
     */
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.LoadedTuple> fetchLoadedTuples([CtParameterImpl][CtTypeReferenceImpl]java.util.Date after) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]javax.persistence.criteria.CriteriaBuilder cb = [CtInvocationImpl][CtFieldReadImpl]entityManager.getCriteriaBuilder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.persistence.criteria.CriteriaQuery<[CtTypeReferenceImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.LoadedTuple> query = [CtInvocationImpl][CtVariableReadImpl]cb.createQuery([CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.LoadedTuple.class);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]javax.persistence.criteria.Root<[CtTypeReferenceImpl]gov.cms.bfd.model.rif.LoadedFile> f = [CtInvocationImpl][CtVariableReadImpl]query.from([CtFieldReadImpl]gov.cms.bfd.model.rif.LoadedFile.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.persistence.criteria.Join<[CtTypeReferenceImpl]gov.cms.bfd.model.rif.LoadedFile, [CtTypeReferenceImpl]gov.cms.bfd.model.rif.LoadedBatch> b = [CtInvocationImpl][CtVariableReadImpl]f.join([CtLiteralImpl]"batches");
        [CtAssignmentImpl][CtVariableWriteImpl]query = [CtInvocationImpl][CtVariableReadImpl]query.select([CtInvocationImpl][CtVariableReadImpl]cb.construct([CtFieldReadImpl]gov.cms.bfd.server.war.stu3.providers.LoadedFilterManager.LoadedTuple.class, [CtInvocationImpl][CtVariableReadImpl]f.get([CtLiteralImpl]"loadedFileId"), [CtInvocationImpl][CtVariableReadImpl]f.get([CtLiteralImpl]"created"), [CtInvocationImpl][CtVariableReadImpl]cb.max([CtInvocationImpl][CtVariableReadImpl]b.get([CtLiteralImpl]"created"))));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]after != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]query = [CtInvocationImpl][CtVariableReadImpl]query.where([CtInvocationImpl][CtVariableReadImpl]cb.greaterThan([CtInvocationImpl][CtVariableReadImpl]b.get([CtLiteralImpl]"created"), [CtVariableReadImpl]after));
        }
        [CtAssignmentImpl][CtVariableWriteImpl]query = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]query.groupBy([CtInvocationImpl][CtVariableReadImpl]f.get([CtLiteralImpl]"loadedFileId"), [CtInvocationImpl][CtVariableReadImpl]f.get([CtLiteralImpl]"created")).orderBy([CtInvocationImpl][CtVariableReadImpl]cb.desc([CtInvocationImpl][CtVariableReadImpl]f.get([CtLiteralImpl]"created")));
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]entityManager.createQuery([CtVariableReadImpl]query).getResultList();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Fetch all the files that are currently loaded.
     *
     * @return the LoadedFiles or an empty list
     */
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.model.rif.LoadedFile> fetchLoadedFiles() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]entityManager.createQuery([CtLiteralImpl]"select f from LoadedFile f", [CtFieldReadImpl]gov.cms.bfd.model.rif.LoadedFile.class).getResultList();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Fetch all the batches associated with LoadedFile.
     *
     * @param loadedFileId
     * 		of the LoadedFile
     * @return a list of LoadedBatches or an empty list
     */
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]gov.cms.bfd.model.rif.LoadedBatch> fetchLoadedBatches([CtParameterImpl][CtTypeReferenceImpl]long loadedFileId) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]entityManager.createQuery([CtLiteralImpl]"select b from LoadedBatch b where b.loadedFileId = :loadedFileId", [CtFieldReadImpl]gov.cms.bfd.model.rif.LoadedBatch.class).setParameter([CtLiteralImpl]"loadedFileId", [CtVariableReadImpl]loadedFileId).getResultList();
    }
}