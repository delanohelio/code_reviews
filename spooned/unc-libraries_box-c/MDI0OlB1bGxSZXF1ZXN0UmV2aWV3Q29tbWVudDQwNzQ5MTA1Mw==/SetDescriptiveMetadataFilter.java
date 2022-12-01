[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright 2008 The University of North Carolina at Chapel Hill
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
[CtPackageDeclarationImpl]package edu.unc.lib.dl.data.ingest.solr.filter;
[CtImportImpl]import java.io.InputStreamReader;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import edu.unc.lib.dl.xml.JDOMNamespaceUtil;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import edu.unc.lib.dl.fedora.FedoraException;
[CtImportImpl]import java.util.Properties;
[CtUnresolvedImport]import edu.unc.lib.dl.xml.JDOMQueryUtil;
[CtImportImpl]import org.slf4j.Logger;
[CtImportImpl]import java.util.Date;
[CtUnresolvedImport]import org.apache.jena.rdf.model.Statement;
[CtUnresolvedImport]import org.jdom2.Attribute;
[CtUnresolvedImport]import edu.unc.lib.dl.search.solr.model.IndexDocumentBean;
[CtUnresolvedImport]import edu.unc.lib.dl.util.VocabularyHelperManager;
[CtUnresolvedImport]import org.apache.jena.rdf.model.Resource;
[CtUnresolvedImport]import edu.unc.lib.dl.data.ingest.solr.indexing.DocumentIndexingPackage;
[CtUnresolvedImport]import edu.unc.lib.dl.data.ingest.solr.exception.IndexingException;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import edu.unc.lib.dl.rdf.DcElements;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import edu.unc.lib.dl.rdf.Ebucore;
[CtUnresolvedImport]import org.jdom2.Element;
[CtClassImpl][CtJavaDocImpl]/**
 * Filter which sets descriptive metadata information, generally pulled from MODS
 *
 * @author bbpennel
 */
public class SetDescriptiveMetadataFilter implements [CtTypeReferenceImpl]edu.unc.lib.dl.data.ingest.solr.filter.IndexDocumentFilter {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger log = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]edu.unc.lib.dl.data.ingest.solr.filter.SetDescriptiveMetadataFilter.class);

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Properties languageCodeMap;

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String AFFIL_URI = [CtLiteralImpl]"http://cdr.unc.edu/vocabulary/Affiliation";

    [CtFieldImpl]private [CtTypeReferenceImpl]edu.unc.lib.dl.util.VocabularyHelperManager vocabManager;

    [CtConstructorImpl]public SetDescriptiveMetadataFilter() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]languageCodeMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Properties();
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]languageCodeMap.load([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.InputStreamReader([CtInvocationImpl][CtInvocationImpl][CtThisAccessImpl]this.getClass().getResourceAsStream([CtLiteralImpl]"iso639LangMappings.txt")));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]edu.unc.lib.dl.data.ingest.solr.filter.SetDescriptiveMetadataFilter.log.error([CtLiteralImpl]"Failed to load code language mappings", [CtVariableReadImpl]e);
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void filter([CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.data.ingest.solr.indexing.DocumentIndexingPackage dip) throws [CtTypeReferenceImpl]edu.unc.lib.dl.data.ingest.solr.exception.IndexingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.unc.lib.dl.search.solr.model.IndexDocumentBean idb = [CtInvocationImpl][CtVariableReadImpl]dip.getDocument();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.jdom2.Element mods = [CtInvocationImpl][CtVariableReadImpl]dip.getMods();
        [CtInvocationImpl][CtVariableReadImpl]idb.setKeyword([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]mods != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtThisAccessImpl]this.extractTitles([CtVariableReadImpl]mods, [CtVariableReadImpl]idb);
            [CtInvocationImpl][CtThisAccessImpl]this.extractNamesAndAffiliations([CtVariableReadImpl]mods, [CtVariableReadImpl]idb, [CtLiteralImpl]true);
            [CtInvocationImpl][CtThisAccessImpl]this.extractAbstract([CtVariableReadImpl]mods, [CtVariableReadImpl]idb);
            [CtInvocationImpl][CtThisAccessImpl]this.extractCollectionId([CtVariableReadImpl]mods, [CtVariableReadImpl]idb);
            [CtInvocationImpl][CtThisAccessImpl]this.extractFindingAidLink([CtVariableReadImpl]mods, [CtVariableReadImpl]idb);
            [CtInvocationImpl][CtThisAccessImpl]this.extractLanguages([CtVariableReadImpl]mods, [CtVariableReadImpl]idb);
            [CtInvocationImpl][CtThisAccessImpl]this.extractSubjects([CtVariableReadImpl]mods, [CtVariableReadImpl]idb);
            [CtInvocationImpl][CtThisAccessImpl]this.extractDateCreated([CtVariableReadImpl]mods, [CtVariableReadImpl]idb);
            [CtInvocationImpl][CtThisAccessImpl]this.extractIdentifiers([CtVariableReadImpl]mods, [CtVariableReadImpl]idb);
            [CtInvocationImpl][CtThisAccessImpl]this.extractCitation([CtVariableReadImpl]mods, [CtVariableReadImpl]idb);
            [CtInvocationImpl][CtThisAccessImpl]this.extractKeywords([CtVariableReadImpl]mods, [CtVariableReadImpl]idb);
        } else [CtBlockImpl]{
            [CtCommentImpl]// TODO basic DC mappings
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dip.getDocument().getTitle() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setTitle([CtInvocationImpl]getAlternativeTitle([CtVariableReadImpl]dip));
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dip.getDocument().getDateCreated() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setDateCreated([CtInvocationImpl][CtVariableReadImpl]idb.getDateAdded());
        }
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]idb.getKeyword().add([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dip.getPid().getId());
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String getAlternativeTitle([CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.data.ingest.solr.indexing.DocumentIndexingPackage dip) throws [CtTypeReferenceImpl]edu.unc.lib.dl.fedora.FedoraException, [CtTypeReferenceImpl]edu.unc.lib.dl.data.ingest.solr.exception.IndexingException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.jena.rdf.model.Resource resc = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dip.getContentObject().getResource();
        [CtIfImpl][CtCommentImpl]// Use dc:title as a default
        if ([CtInvocationImpl][CtVariableReadImpl]resc.hasProperty([CtTypeAccessImpl]DcElements.title)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.jena.rdf.model.Statement dcTitle = [CtInvocationImpl][CtVariableReadImpl]resc.getProperty([CtTypeAccessImpl]DcElements.title);
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]dcTitle.getString();
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]resc.hasProperty([CtTypeAccessImpl]Ebucore.filename)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// fall back to filename if one is present
            [CtTypeReferenceImpl]org.apache.jena.rdf.model.Statement filename = [CtInvocationImpl][CtVariableReadImpl]resc.getProperty([CtTypeAccessImpl]Ebucore.filename);
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]filename.getString();
        } else [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// Use the object's id as the title as a final option
            return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dip.getPid().getId();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void extractTitles([CtParameterImpl][CtTypeReferenceImpl]org.jdom2.Element mods, [CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.search.solr.model.IndexDocumentBean idb) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> titles = [CtInvocationImpl][CtVariableReadImpl]mods.getChildren([CtLiteralImpl]"titleInfo", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String mainTitle = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> otherTitles = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object titleInfoObj : [CtVariableReadImpl]titles) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.jdom2.Element titleInfoEl = [CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (titleInfoObj));
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object titleObj : [CtInvocationImpl][CtVariableReadImpl]titleInfoEl.getChildren()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.jdom2.Element titleEl = [CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (titleObj));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]mainTitle == [CtLiteralImpl]null) && [CtInvocationImpl][CtLiteralImpl]"title".equalsIgnoreCase([CtInvocationImpl][CtVariableReadImpl]titleEl.getName())) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]mainTitle = [CtInvocationImpl][CtVariableReadImpl]titleEl.getValue();
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]otherTitles.add([CtInvocationImpl][CtVariableReadImpl]titleEl.getValue());
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]mainTitle != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]mainTitle.equals([CtLiteralImpl]""))) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setTitle([CtVariableReadImpl]mainTitle);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]otherTitles.size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setOtherTitle([CtVariableReadImpl]otherTitles);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setOtherTitle([CtLiteralImpl]null);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void extractNamesAndAffiliations([CtParameterImpl][CtTypeReferenceImpl]org.jdom2.Element mods, [CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.search.solr.model.IndexDocumentBean idb, [CtParameterImpl][CtTypeReferenceImpl]boolean splitDepartments) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> names = [CtInvocationImpl][CtVariableReadImpl]mods.getChildren([CtLiteralImpl]"name", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> creators = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> contributors = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.jdom2.Element nameEl;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object nameObj : [CtVariableReadImpl]names) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]nameEl = [CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (nameObj));
            [CtLocalVariableImpl][CtCommentImpl]// First see if there is a display form
            [CtTypeReferenceImpl]java.lang.String nameValue = [CtInvocationImpl][CtVariableReadImpl]nameEl.getChildText([CtLiteralImpl]"displayForm", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nameValue == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// If there was no displayForm, then try to get the name parts.
                [CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> nameParts = [CtInvocationImpl][CtVariableReadImpl]nameEl.getChildren([CtLiteralImpl]"namePart", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]nameParts.size() == [CtLiteralImpl]1) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]nameValue = [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]org.jdom2.Element) ([CtVariableReadImpl]nameParts.get([CtLiteralImpl]0))).getValue();
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]nameParts.size() > [CtLiteralImpl]1) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.jdom2.Element genericPart = [CtInvocationImpl][CtTypeAccessImpl]edu.unc.lib.dl.xml.JDOMQueryUtil.getElementByAttribute([CtVariableReadImpl]nameParts, [CtLiteralImpl]"type", [CtLiteralImpl]null);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]genericPart != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]nameValue = [CtInvocationImpl][CtVariableReadImpl]genericPart.getValue();
                    } else [CtBlockImpl]{
                        [CtLocalVariableImpl][CtCommentImpl]// If there were multiple non-generic name parts, then try to piece them together
                        [CtTypeReferenceImpl]org.jdom2.Element givenPart = [CtInvocationImpl][CtTypeAccessImpl]edu.unc.lib.dl.xml.JDOMQueryUtil.getElementByAttribute([CtVariableReadImpl]nameParts, [CtLiteralImpl]"type", [CtLiteralImpl]"given");
                        [CtLocalVariableImpl][CtTypeReferenceImpl]org.jdom2.Element familyPart = [CtInvocationImpl][CtTypeAccessImpl]edu.unc.lib.dl.xml.JDOMQueryUtil.getElementByAttribute([CtVariableReadImpl]nameParts, [CtLiteralImpl]"type", [CtLiteralImpl]"family");
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder nameBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]familyPart != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]nameBuilder.append([CtInvocationImpl][CtVariableReadImpl]familyPart.getValue());
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]givenPart != [CtLiteralImpl]null) [CtBlockImpl]{
                                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nameBuilder.append([CtLiteralImpl]',').append([CtLiteralImpl]' ');
                            }
                        }
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]givenPart != [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]nameBuilder.append([CtInvocationImpl][CtVariableReadImpl]givenPart.getValue());
                        }
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]nameBuilder.length() > [CtLiteralImpl]0) [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]nameValue = [CtInvocationImpl][CtVariableReadImpl]nameBuilder.toString();
                        } else [CtBlockImpl]{
                            [CtAssignmentImpl][CtCommentImpl]// Non-sensical name, just use the first available value.
                            [CtVariableWriteImpl]nameValue = [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]org.jdom2.Element) ([CtVariableReadImpl]nameParts.get([CtLiteralImpl]0))).getValue();
                        }
                    }
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nameValue != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]contributors.add([CtVariableReadImpl]nameValue);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> roles = [CtInvocationImpl][CtVariableReadImpl]nameEl.getChildren([CtLiteralImpl]"role", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
                [CtLocalVariableImpl][CtCommentImpl]// Person is automatically a creator if no role is provided.
                [CtTypeReferenceImpl]boolean isCreator = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]roles.size() == [CtLiteralImpl]0;
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]isCreator) [CtBlockImpl]{
                    [CtForEachImpl][CtCommentImpl]// If roles were provided, then check to see if any of them are creators.  If so, store as creator.
                    for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object role : [CtVariableReadImpl]roles) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> roleTerms = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (role)).getChildren([CtLiteralImpl]"roleTerm", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
                        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object roleTerm : [CtVariableReadImpl]roleTerms) [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"creator".equalsIgnoreCase([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (roleTerm)).getValue())) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]isCreator = [CtLiteralImpl]true;
                                [CtBreakImpl]break;
                            }
                            [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"author".equalsIgnoreCase([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (roleTerm)).getValue())) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]isCreator = [CtLiteralImpl]true;
                                [CtBreakImpl]break;
                            }
                            [CtIfImpl]if ([CtVariableReadImpl]isCreator) [CtBlockImpl]{
                                [CtBreakImpl]break;
                            }
                        }
                    }
                }
                [CtIfImpl]if ([CtVariableReadImpl]isCreator) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]creators.add([CtVariableReadImpl]nameValue);
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]contributors.size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setContributor([CtVariableReadImpl]contributors);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setContributor([CtLiteralImpl]null);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]creators.size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setCreator([CtVariableReadImpl]creators);
            [CtInvocationImpl][CtVariableReadImpl]idb.setCreatorSort([CtInvocationImpl][CtVariableReadImpl]creators.get([CtLiteralImpl]0));
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setCreator([CtLiteralImpl]null);
            [CtInvocationImpl][CtVariableReadImpl]idb.setCreatorSort([CtLiteralImpl]null);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>>> authTerms = [CtInvocationImpl][CtFieldReadImpl]vocabManager.getAuthoritativeForms([CtInvocationImpl][CtVariableReadImpl]idb.getPid(), [CtVariableReadImpl]mods);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> flattened = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]authTerms != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String>> affiliationTerms = [CtInvocationImpl][CtVariableReadImpl]authTerms.get([CtFieldReadImpl]edu.unc.lib.dl.data.ingest.solr.filter.SetDescriptiveMetadataFilter.AFFIL_URI);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]affiliationTerms != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForEachImpl][CtCommentImpl]// Make the departments for the whole document into a form solr can take
                for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> path : [CtVariableReadImpl]affiliationTerms) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]flattened.addAll([CtVariableReadImpl]path);
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]flattened.size() == [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]idb.setDepartment([CtLiteralImpl]null);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]idb.setDepartment([CtVariableReadImpl]flattened);
                }
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void extractAbstract([CtParameterImpl][CtTypeReferenceImpl]org.jdom2.Element mods, [CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.search.solr.model.IndexDocumentBean idb) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String abstractText = [CtInvocationImpl][CtVariableReadImpl]mods.getChildText([CtLiteralImpl]"abstract", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]abstractText != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setAbstractText([CtInvocationImpl][CtVariableReadImpl]abstractText.trim());
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setAbstractText([CtLiteralImpl]null);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void extractCollectionId([CtParameterImpl][CtTypeReferenceImpl]org.jdom2.Element mods, [CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.search.solr.model.IndexDocumentBean idb) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> identifiers = [CtInvocationImpl][CtVariableReadImpl]mods.getChildren([CtLiteralImpl]"identifier", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String collectionId = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]identifiers != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]identifiers.size() > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object id : [CtVariableReadImpl]identifiers) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.jdom2.Element aid = [CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (id));
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.jdom2.Attribute type = [CtInvocationImpl][CtVariableReadImpl]aid.getAttribute([CtLiteralImpl]"type");
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.jdom2.Attribute collection = [CtInvocationImpl][CtVariableReadImpl]aid.getAttribute([CtLiteralImpl]"displayLabel");
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtVariableReadImpl]collection == [CtLiteralImpl]null)) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]collection.getValue().equals([CtLiteralImpl]"Collection Number") && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]type.getValue().equals([CtLiteralImpl]"local")) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]collectionId = [CtInvocationImpl][CtVariableReadImpl]aid.getValue();
                    [CtBreakImpl]break;
                }
            }
        }
        [CtInvocationImpl][CtVariableReadImpl]idb.setCollectionId([CtVariableReadImpl]collectionId);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void extractFindingAidLink([CtParameterImpl][CtTypeReferenceImpl]org.jdom2.Element mods, [CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.search.solr.model.IndexDocumentBean idb) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> findingAidLinkEls = [CtInvocationImpl][CtVariableReadImpl]mods.getChildren([CtLiteralImpl]"relatedItem", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> findingAids = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]findingAidLinkEls != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]findingAidLinkEls.size() > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object findingAidObj : [CtVariableReadImpl]findingAidLinkEls) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> findingAidParts = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (findingAidObj)).getChildren();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object findingAid : [CtVariableReadImpl]findingAidParts) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.jdom2.Element aid = [CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (findingAid));
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]aid.getName().equals([CtLiteralImpl]"location")) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> urls = [CtInvocationImpl][CtVariableReadImpl]aid.getChildren();
                        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object url : [CtVariableReadImpl]urls) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]org.jdom2.Element urlType = [CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (url));
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String displayLabel = [CtInvocationImpl][CtVariableReadImpl]urlType.getAttributeValue([CtLiteralImpl]"displayLabel");
                            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]displayLabel.toLowerCase().equals([CtLiteralImpl]"link to finding aid")) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]findingAids.add([CtInvocationImpl][CtVariableReadImpl]urlType.getValue());
                            }
                        }
                    }
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]findingAids.size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setFindingAidLink([CtVariableReadImpl]findingAids);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setFindingAidLink([CtLiteralImpl]null);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void extractSubjects([CtParameterImpl][CtTypeReferenceImpl]org.jdom2.Element mods, [CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.search.solr.model.IndexDocumentBean idb) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> subjectEls = [CtInvocationImpl][CtVariableReadImpl]mods.getChildren([CtLiteralImpl]"subject", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> subjects = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]subjectEls.size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object subjectObj : [CtVariableReadImpl]subjectEls) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> subjectParts = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (subjectObj)).getChildren();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object subjectPart : [CtVariableReadImpl]subjectParts) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]org.jdom2.Element subjectEl = [CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (subjectPart));
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]subjectEl.getChildren().size() == [CtLiteralImpl]0) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]subjects.add([CtInvocationImpl][CtVariableReadImpl]subjectEl.getValue());
                    }
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]subjects.size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setSubject([CtVariableReadImpl]subjects);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setSubject([CtLiteralImpl]null);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void extractLanguages([CtParameterImpl][CtTypeReferenceImpl]org.jdom2.Element mods, [CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.search.solr.model.IndexDocumentBean idb) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> languageEls = [CtInvocationImpl][CtVariableReadImpl]mods.getChildren([CtLiteralImpl]"language", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> languages = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]languageEls.size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String languageTerm = [CtLiteralImpl]null;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object languageObj : [CtVariableReadImpl]languageEls) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Our schema only allows for iso639-2b languages at this point.
                [CtVariableWriteImpl]languageTerm = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (languageObj)).getChildText([CtLiteralImpl]"languageTerm", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]languageTerm != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]languageTerm = [CtInvocationImpl][CtFieldReadImpl]languageCodeMap.getProperty([CtInvocationImpl][CtVariableReadImpl]languageTerm.trim());
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]languageTerm != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]languages.add([CtVariableReadImpl]languageTerm);
                    }
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]languages.size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setLanguage([CtVariableReadImpl]languages);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setLanguage([CtLiteralImpl]null);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the preferred date to use as the date created. Order of preference is
     * the first date created found, then the first date issued, then the first
     * date captured.
     *
     * @param mods
     * @param idb
     */
    private [CtTypeReferenceImpl]void extractDateCreated([CtParameterImpl][CtTypeReferenceImpl]org.jdom2.Element mods, [CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.search.solr.model.IndexDocumentBean idb) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> originInfoEls = [CtInvocationImpl][CtVariableReadImpl]mods.getChildren([CtLiteralImpl]"originInfo", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date dateCreated = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date dateIssued = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Date dateCaptured = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]originInfoEls.size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object originInfoObj : [CtVariableReadImpl]originInfoEls) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.jdom2.Element originInfoEl = [CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (originInfoObj));
                [CtAssignmentImpl][CtVariableWriteImpl]dateCreated = [CtInvocationImpl][CtTypeAccessImpl]edu.unc.lib.dl.xml.JDOMQueryUtil.parseISO6392bDateChild([CtVariableReadImpl]originInfoEl, [CtLiteralImpl]"dateCreated", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dateCreated != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtBreakImpl]break;
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dateIssued == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]dateIssued = [CtInvocationImpl][CtTypeAccessImpl]edu.unc.lib.dl.xml.JDOMQueryUtil.parseISO6392bDateChild([CtVariableReadImpl]originInfoEl, [CtLiteralImpl]"dateIssued", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]dateIssued == [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]dateCaptured == [CtLiteralImpl]null)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]dateCaptured = [CtInvocationImpl][CtTypeAccessImpl]edu.unc.lib.dl.xml.JDOMQueryUtil.parseISO6392bDateChild([CtVariableReadImpl]originInfoEl, [CtLiteralImpl]"dateCaptured", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dateCreated != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]idb.setDateCreated([CtVariableReadImpl]dateCreated);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dateIssued != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]idb.setDateCreated([CtVariableReadImpl]dateIssued);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]dateCaptured != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]idb.setDateCreated([CtVariableReadImpl]dateCaptured);
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void extractIdentifiers([CtParameterImpl][CtTypeReferenceImpl]org.jdom2.Element mods, [CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.search.solr.model.IndexDocumentBean idb) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> identifierEls = [CtInvocationImpl][CtVariableReadImpl]mods.getChildren([CtLiteralImpl]"identifier", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> identifiers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object identifierObj : [CtVariableReadImpl]identifierEls) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder identifierBuilder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.jdom2.Element identifierEl = [CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (identifierObj));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String idType = [CtInvocationImpl][CtVariableReadImpl]identifierEl.getAttributeValue([CtLiteralImpl]"type");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]idType != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]idType.equalsIgnoreCase([CtLiteralImpl]"uri")) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtInvocationImpl][CtVariableReadImpl]identifierBuilder.append([CtVariableReadImpl]idType);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String idValue = [CtInvocationImpl][CtVariableReadImpl]identifierEl.getValue();
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]identifierBuilder.append([CtLiteralImpl]'|').append([CtVariableReadImpl]idValue);
            [CtInvocationImpl][CtVariableReadImpl]identifiers.add([CtInvocationImpl][CtVariableReadImpl]identifierBuilder.toString());
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]idb.getKeyword().add([CtVariableReadImpl]idValue);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]identifiers.size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setIdentifier([CtVariableReadImpl]identifiers);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setIdentifier([CtLiteralImpl]null);
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void extractKeywords([CtParameterImpl][CtTypeReferenceImpl]org.jdom2.Element mods, [CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.search.solr.model.IndexDocumentBean idb) [CtBlockImpl]{
        [CtInvocationImpl][CtThisAccessImpl]this.addValuesToList([CtInvocationImpl][CtVariableReadImpl]idb.getKeyword(), [CtInvocationImpl][CtVariableReadImpl]mods.getChildren([CtLiteralImpl]"genre", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS));
        [CtInvocationImpl][CtThisAccessImpl]this.addValuesToList([CtInvocationImpl][CtVariableReadImpl]idb.getKeyword(), [CtInvocationImpl][CtVariableReadImpl]mods.getChildren([CtLiteralImpl]"typeOfResource", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS));
        [CtInvocationImpl][CtThisAccessImpl]this.addValuesToList([CtInvocationImpl][CtVariableReadImpl]idb.getKeyword(), [CtInvocationImpl][CtVariableReadImpl]mods.getChildren([CtLiteralImpl]"note", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> physicalDescription = [CtInvocationImpl][CtVariableReadImpl]mods.getChildren([CtLiteralImpl]"physicalDescription", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object childObj : [CtVariableReadImpl]physicalDescription) [CtBlockImpl]{
            [CtInvocationImpl][CtThisAccessImpl]this.addValuesToList([CtInvocationImpl][CtVariableReadImpl]idb.getKeyword(), [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (childObj)).getChildren([CtLiteralImpl]"note", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS));
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> relatedItemEls = [CtInvocationImpl][CtVariableReadImpl]mods.getChildren([CtLiteralImpl]"relatedItem", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object childObj : [CtVariableReadImpl]relatedItemEls) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> childChildren = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (childObj)).getChildren();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object childChildObj : [CtVariableReadImpl]childChildren) [CtBlockImpl]{
                [CtInvocationImpl][CtThisAccessImpl]this.addValuesToList([CtInvocationImpl][CtVariableReadImpl]idb.getKeyword(), [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (childChildObj)).getChildren());
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void addValuesToList([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> values, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> elements) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]elements == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object elementObj : [CtVariableReadImpl]elements) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String value = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]org.jdom2.Element) (elementObj)).getValue();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]values.add([CtVariableReadImpl]value);
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void extractCitation([CtParameterImpl][CtTypeReferenceImpl]org.jdom2.Element mods, [CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.search.solr.model.IndexDocumentBean idb) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.jdom2.Element citationEl = [CtInvocationImpl][CtTypeAccessImpl]edu.unc.lib.dl.xml.JDOMQueryUtil.getChildByAttribute([CtVariableReadImpl]mods, [CtLiteralImpl]"note", [CtTypeAccessImpl]JDOMNamespaceUtil.MODS_V3_NS, [CtLiteralImpl]"type", [CtLiteralImpl]"citation/reference");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]citationEl != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setCitation([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]citationEl.getValue().trim());
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]idb.setCitation([CtLiteralImpl]null);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setVocabManager([CtParameterImpl][CtTypeReferenceImpl]edu.unc.lib.dl.util.VocabularyHelperManager vocabManager) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.vocabManager = [CtVariableReadImpl]vocabManager;
    }
}