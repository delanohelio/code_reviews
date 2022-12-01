[CompilationUnitImpl][CtCommentImpl]/* Copyright (C) 2001-2016 Food and Agriculture Organization of the
United Nations (FAO-UN), United Nations World Food Programme (WFP)
and United Nations Environment Programme (UNEP)

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or (at
your option) any later version.

This program is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301, USA

Contact: Jeroen Ticheler - FAO - Viale delle Terme di Caracalla 2,
Rome - Italy. email: geonetwork@osgeo.org
 */
[CtPackageDeclarationImpl]package jeeves.transaction;
[CtUnresolvedImport]import org.springframework.transaction.TransactionSystemException;
[CtUnresolvedImport]import org.springframework.transaction.PlatformTransactionManager;
[CtUnresolvedImport]import javax.persistence.RollbackException;
[CtUnresolvedImport]import org.springframework.transaction.TransactionDefinition;
[CtUnresolvedImport]import org.springframework.transaction.TransactionStatus;
[CtImportImpl]import java.util.Collection;
[CtUnresolvedImport]import org.springframework.transaction.support.DefaultTransactionDefinition;
[CtUnresolvedImport]import org.fao.geonet.utils.Log;
[CtUnresolvedImport]import org.springframework.context.ApplicationContext;
[CtUnresolvedImport]import javax.annotation.Nullable;
[CtClassImpl][CtJavaDocImpl]/**
 * Declares the cut-points/places where transactions are needed in Geonetwork.  Each module that
 * needs transactions needs to define a class like this and add it as a bean in the spring
 * configuration.
 * <p/>
 * Created by Jesse on 3/10/14.
 */
public class TransactionManager {
    [CtMethodImpl]public static <[CtTypeParameterImpl]V> [CtTypeParameterReferenceImpl]V runInTransaction([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]org.springframework.context.ApplicationContext context, [CtParameterImpl][CtTypeReferenceImpl]jeeves.transaction.TransactionManager.TransactionRequirement transactionRequirement, [CtParameterImpl][CtTypeReferenceImpl]jeeves.transaction.TransactionManager.CommitBehavior commitBehavior, [CtParameterImpl][CtTypeReferenceImpl]boolean readOnly, [CtParameterImpl]final [CtTypeReferenceImpl]jeeves.transaction.TransactionTask<[CtTypeParameterReferenceImpl]V> action) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.springframework.transaction.PlatformTransactionManager transactionManager = [CtInvocationImpl][CtVariableReadImpl]context.getBean([CtFieldReadImpl]org.springframework.transaction.PlatformTransactionManager.class);
        [CtLocalVariableImpl]final [CtArrayTypeReferenceImpl]java.lang.Throwable[] exception = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Throwable[[CtLiteralImpl]1];
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.transaction.TransactionStatus transaction = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isNewTransaction = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean rolledBack = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeParameterReferenceImpl]V result = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.transaction.support.DefaultTransactionDefinition definition = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.springframework.transaction.support.DefaultTransactionDefinition([CtFieldReadImpl][CtVariableReadImpl]transactionRequirement.propagationId);
            [CtInvocationImpl][CtVariableReadImpl]definition.setName([CtVariableReadImpl]name);
            [CtInvocationImpl][CtVariableReadImpl]definition.setReadOnly([CtVariableReadImpl]readOnly);
            [CtAssignmentImpl][CtVariableWriteImpl]transaction = [CtInvocationImpl][CtVariableReadImpl]transactionManager.getTransaction([CtVariableReadImpl]definition);
            [CtAssignmentImpl][CtVariableWriteImpl]isNewTransaction = [CtInvocationImpl][CtVariableReadImpl]transaction.isNewTransaction();
            [CtIfImpl]if ([CtVariableReadImpl]isNewTransaction) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl]jeeves.transaction.TransactionManager.fireNewTransaction([CtVariableReadImpl]context, [CtVariableReadImpl]transactionManager, [CtVariableReadImpl]transaction);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.utils.Log.warning([CtTypeAccessImpl]Log.JEEVES, [CtLiteralImpl]"New transaction:", [CtVariableReadImpl]t);
                    [CtCommentImpl]// warning as we continue with action below
                }
            }
            [CtAssignmentImpl][CtVariableWriteImpl]result = [CtInvocationImpl][CtVariableReadImpl]action.doInTransaction([CtVariableReadImpl]transaction);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable e) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.utils.Log.error([CtTypeAccessImpl]Log.JEEVES, [CtLiteralImpl]"Error occurred within a transaction", [CtVariableReadImpl]e);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtArrayReadImpl][CtVariableReadImpl]exception[[CtLiteralImpl]0] == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]exception[[CtLiteralImpl]0] = [CtVariableReadImpl]e;
            }
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl]jeeves.transaction.TransactionManager.doRollback([CtVariableReadImpl]context, [CtVariableReadImpl]transactionManager, [CtVariableReadImpl]transaction);
            } finally [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]rolledBack = [CtLiteralImpl]true;
            }
        } finally [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]readOnly && [CtUnaryOperatorImpl](![CtVariableReadImpl]rolledBack)) [CtBlockImpl]{
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtInvocationImpl]jeeves.transaction.TransactionManager.doRollback([CtVariableReadImpl]context, [CtVariableReadImpl]transactionManager, [CtVariableReadImpl]transaction);
                    } finally [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]rolledBack = [CtLiteralImpl]true;
                    }
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]rolledBack) && [CtBinaryOperatorImpl]([CtVariableReadImpl]isNewTransaction || [CtBinaryOperatorImpl]([CtVariableReadImpl]commitBehavior == [CtFieldReadImpl][CtTypeAccessImpl]jeeves.transaction.TransactionManager.CommitBehavior.[CtFieldReferenceImpl]ALWAYS_COMMIT))) [CtBlockImpl]{
                    [CtInvocationImpl]jeeves.transaction.TransactionManager.doCommit([CtVariableReadImpl]context, [CtVariableReadImpl]transactionManager, [CtVariableReadImpl]transaction);
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.transaction.TransactionSystemException e) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]e.getOriginalException() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]javax.persistence.RollbackException)) [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.utils.Log.error([CtTypeAccessImpl]Log.JEEVES, [CtLiteralImpl]"ERROR committing transaction, will try to rollback", [CtVariableReadImpl]e);
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]rolledBack) [CtBlockImpl]{
                        [CtTryImpl]try [CtBlockImpl]{
                            [CtInvocationImpl]jeeves.transaction.TransactionManager.doRollback([CtVariableReadImpl]context, [CtVariableReadImpl]transactionManager, [CtVariableReadImpl]transaction);
                        } finally [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]rolledBack = [CtLiteralImpl]true;
                        }
                    }
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.utils.Log.debug([CtTypeAccessImpl]Log.JEEVES, [CtLiteralImpl]"ERROR committing transaction, will try to rollback", [CtVariableReadImpl]e);
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]rolledBack) [CtBlockImpl]{
                        [CtTryImpl]try [CtBlockImpl]{
                            [CtInvocationImpl]jeeves.transaction.TransactionManager.doRollback([CtVariableReadImpl]context, [CtVariableReadImpl]transactionManager, [CtVariableReadImpl]transaction);
                        } finally [CtBlockImpl]{
                            [CtAssignmentImpl][CtVariableWriteImpl]rolledBack = [CtLiteralImpl]true;
                        }
                    }
                }
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.utils.Log.error([CtTypeAccessImpl]Log.JEEVES, [CtLiteralImpl]"ERROR committing transaction, will try to rollback", [CtVariableReadImpl]t);
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]rolledBack) [CtBlockImpl]{
                    [CtTryImpl]try [CtBlockImpl]{
                        [CtInvocationImpl]jeeves.transaction.TransactionManager.doRollback([CtVariableReadImpl]context, [CtVariableReadImpl]transactionManager, [CtVariableReadImpl]transaction);
                    } finally [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]rolledBack = [CtLiteralImpl]true;
                    }
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtArrayReadImpl][CtVariableReadImpl]exception[[CtLiteralImpl]0] != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtArrayReadImpl][CtVariableReadImpl]exception[[CtLiteralImpl]0] instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.RuntimeException) [CtBlockImpl]{
                [CtThrowImpl]throw [CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.RuntimeException) ([CtVariableReadImpl]exception[[CtLiteralImpl]0]));
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtArrayReadImpl][CtVariableReadImpl]exception[[CtLiteralImpl]0] instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.lang.Error) [CtBlockImpl]{
                [CtThrowImpl]throw [CtArrayReadImpl](([CtTypeReferenceImpl]java.lang.Error) ([CtVariableReadImpl]exception[[CtLiteralImpl]0]));
            } else [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtArrayReadImpl][CtVariableReadImpl]exception[[CtLiteralImpl]0]);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]protected static [CtTypeReferenceImpl]void doCommit([CtParameterImpl][CtTypeReferenceImpl]org.springframework.context.ApplicationContext context, [CtParameterImpl][CtTypeReferenceImpl]org.springframework.transaction.PlatformTransactionManager transactionManager, [CtParameterImpl][CtTypeReferenceImpl]org.springframework.transaction.TransactionStatus transaction) [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl]jeeves.transaction.TransactionManager.fireBeforeCommit([CtVariableReadImpl]context, [CtVariableReadImpl]transactionManager, [CtVariableReadImpl]transaction);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.utils.Log.warning([CtTypeAccessImpl]Log.JEEVES, [CtLiteralImpl]"Commit transaction - before:", [CtVariableReadImpl]t);
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]transactionManager.commit([CtVariableReadImpl]transaction);
        } finally [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl]jeeves.transaction.TransactionManager.fireAfterCommit([CtVariableReadImpl]context, [CtVariableReadImpl]transactionManager, [CtVariableReadImpl]transaction);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.utils.Log.warning([CtTypeAccessImpl]Log.JEEVES, [CtLiteralImpl]"Commit transaction - after:", [CtVariableReadImpl]t);
            }
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void fireAfterCommit([CtParameterImpl][CtTypeReferenceImpl]org.springframework.context.ApplicationContext context, [CtParameterImpl][CtTypeReferenceImpl]org.springframework.transaction.PlatformTransactionManager transactionManager, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]org.springframework.transaction.TransactionStatus transaction) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Throwable afterCommitFailure = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]jeeves.transaction.AfterCommitTransactionListener> listeners = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getBeansOfType([CtFieldReadImpl]jeeves.transaction.AfterCommitTransactionListener.class).values();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]jeeves.transaction.AfterCommitTransactionListener listener : [CtVariableReadImpl]listeners) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]listener.afterCommit([CtVariableReadImpl]transaction);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.utils.Log.debug([CtTypeAccessImpl]Log.JEEVES, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Listener " + [CtInvocationImpl][CtVariableReadImpl]listener.toString()) + [CtLiteralImpl]" newTransaction callback failed: ") + [CtVariableReadImpl]t);
                [CtAssignmentImpl][CtVariableWriteImpl]afterCommitFailure = [CtVariableReadImpl]t;
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]afterCommitFailure != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtVariableReadImpl]afterCommitFailure;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void fireBeforeCommit([CtParameterImpl][CtTypeReferenceImpl]org.springframework.context.ApplicationContext context, [CtParameterImpl][CtTypeReferenceImpl]org.springframework.transaction.PlatformTransactionManager transactionManager, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]org.springframework.transaction.TransactionStatus transaction) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Throwable beforeCommitFailure = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]jeeves.transaction.BeforeCommitTransactionListener> listeners = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getBeansOfType([CtFieldReadImpl]jeeves.transaction.BeforeCommitTransactionListener.class).values();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]jeeves.transaction.BeforeCommitTransactionListener listener : [CtVariableReadImpl]listeners) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]listener.beforeCommit([CtVariableReadImpl]transaction);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.utils.Log.debug([CtTypeAccessImpl]Log.JEEVES, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Listener " + [CtInvocationImpl][CtVariableReadImpl]listener.toString()) + [CtLiteralImpl]" newTransaction callback failed: ") + [CtVariableReadImpl]t);
                [CtAssignmentImpl][CtVariableWriteImpl]beforeCommitFailure = [CtVariableReadImpl]t;
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]beforeCommitFailure != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtVariableReadImpl]beforeCommitFailure;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void fireNewTransaction([CtParameterImpl][CtTypeReferenceImpl]org.springframework.context.ApplicationContext context, [CtParameterImpl][CtTypeReferenceImpl]org.springframework.transaction.PlatformTransactionManager transactionManager, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]org.springframework.transaction.TransactionStatus transaction) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Throwable newTransactionFailure = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]jeeves.transaction.NewTransactionListener> listeners = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getBeansOfType([CtFieldReadImpl]jeeves.transaction.NewTransactionListener.class).values();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]jeeves.transaction.NewTransactionListener listener : [CtVariableReadImpl]listeners) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]listener.newTransaction([CtVariableReadImpl]transaction);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.utils.Log.debug([CtTypeAccessImpl]Log.JEEVES, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Listener " + [CtInvocationImpl][CtVariableReadImpl]listener.toString()) + [CtLiteralImpl]" newTransaction callback failed: ") + [CtVariableReadImpl]t);
                [CtAssignmentImpl][CtVariableWriteImpl]newTransactionFailure = [CtVariableReadImpl]t;
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]newTransactionFailure != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtVariableReadImpl]newTransactionFailure;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void fireAfterRollback([CtParameterImpl][CtTypeReferenceImpl]org.springframework.context.ApplicationContext context, [CtParameterImpl][CtTypeReferenceImpl]org.springframework.transaction.PlatformTransactionManager transactionManager, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]org.springframework.transaction.TransactionStatus transaction) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Throwable afterRollbackFailure = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]jeeves.transaction.AfterRollbackTransactionListener> listeners = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getBeansOfType([CtFieldReadImpl]jeeves.transaction.AfterRollbackTransactionListener.class).values();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]jeeves.transaction.AfterRollbackTransactionListener listener : [CtVariableReadImpl]listeners) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]listener.afterRollback([CtVariableReadImpl]transaction);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.utils.Log.debug([CtTypeAccessImpl]Log.JEEVES, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Listener " + [CtInvocationImpl][CtVariableReadImpl]listener.toString()) + [CtLiteralImpl]" afterRollback callback failed: ") + [CtVariableReadImpl]t);
                [CtAssignmentImpl][CtVariableWriteImpl]afterRollbackFailure = [CtVariableReadImpl]t;
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]afterRollbackFailure != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtVariableReadImpl]afterRollbackFailure;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void fireBeforeRollback([CtParameterImpl][CtTypeReferenceImpl]org.springframework.context.ApplicationContext context, [CtParameterImpl][CtTypeReferenceImpl]org.springframework.transaction.PlatformTransactionManager transactionManager, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]org.springframework.transaction.TransactionStatus transaction) throws [CtTypeReferenceImpl]java.lang.Throwable [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Throwable beforeRollbackFailure = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]jeeves.transaction.BeforeRollbackTransactionListener> listeners = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getBeansOfType([CtFieldReadImpl]jeeves.transaction.BeforeRollbackTransactionListener.class).values();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]jeeves.transaction.BeforeRollbackTransactionListener listener : [CtVariableReadImpl]listeners) [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]listener.beforeRollback([CtVariableReadImpl]transaction);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.utils.Log.debug([CtTypeAccessImpl]Log.JEEVES, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Listener " + [CtInvocationImpl][CtVariableReadImpl]listener.toString()) + [CtLiteralImpl]" beforeRollback callback failed: ") + [CtVariableReadImpl]t);
                [CtAssignmentImpl][CtVariableWriteImpl]beforeRollbackFailure = [CtVariableReadImpl]t;
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]beforeRollbackFailure != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtThrowImpl]throw [CtVariableReadImpl]beforeRollbackFailure;
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void doRollback([CtParameterImpl][CtTypeReferenceImpl]org.springframework.context.ApplicationContext context, [CtParameterImpl][CtTypeReferenceImpl]org.springframework.transaction.PlatformTransactionManager transactionManager, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nullable
    [CtTypeReferenceImpl]org.springframework.transaction.TransactionStatus transaction) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]transaction == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]transaction.isCompleted()) [CtBlockImpl]{
            [CtCommentImpl]// nothing to do
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl]jeeves.transaction.TransactionManager.fireBeforeRollback([CtVariableReadImpl]context, [CtVariableReadImpl]transactionManager, [CtVariableReadImpl]transaction);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.utils.Log.warning([CtTypeAccessImpl]Log.JEEVES, [CtLiteralImpl]"Rolling back transaction - before:", [CtVariableReadImpl]t);
        }
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]transactionManager.rollback([CtVariableReadImpl]transaction);
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.utils.Log.error([CtTypeAccessImpl]Log.JEEVES, [CtLiteralImpl]"ERROR rolling back transaction", [CtVariableReadImpl]t);
        } finally [CtBlockImpl]{
            [CtTryImpl]try [CtBlockImpl]{
                [CtInvocationImpl]jeeves.transaction.TransactionManager.fireAfterRollback([CtVariableReadImpl]context, [CtVariableReadImpl]transactionManager, [CtVariableReadImpl]transaction);
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Throwable t) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]org.fao.geonet.utils.Log.warning([CtTypeAccessImpl]Log.JEEVES, [CtLiteralImpl]"Rolling back transaction - after:", [CtVariableReadImpl]t);
            }
        }
    }

    [CtEnumImpl]public static enum TransactionRequirement {

        [CtEnumValueImpl]CREATE_ONLY_WHEN_NEEDED([CtFieldReadImpl]org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRED),
        [CtEnumValueImpl]THROW_EXCEPTION_IF_NOT_PRESENT([CtFieldReadImpl]org.springframework.transaction.TransactionDefinition.PROPAGATION_MANDATORY),
        [CtEnumValueImpl]CREATE_NEW([CtFieldReadImpl]org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        [CtFieldImpl]private final [CtTypeReferenceImpl]int propagationId;

        [CtConstructorImpl]TransactionRequirement([CtParameterImpl][CtTypeReferenceImpl]int propagation) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.propagationId = [CtVariableReadImpl]propagation;
        }
    }

    [CtEnumImpl]public static enum CommitBehavior {

        [CtEnumValueImpl]ALWAYS_COMMIT,
        [CtEnumValueImpl]ONLY_COMMIT_NEWLY_CREATED_TRANSACTIONS;}
}