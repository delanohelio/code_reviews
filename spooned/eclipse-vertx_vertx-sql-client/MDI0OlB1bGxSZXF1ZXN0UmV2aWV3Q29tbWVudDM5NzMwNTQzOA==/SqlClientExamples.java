[CompilationUnitImpl][CtCommentImpl]/* Copyright (C) 2020 IBM Corporation

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
[CtPackageDeclarationImpl]package examples;
[CtUnresolvedImport]import io.vertx.sqlclient.RowStream;
[CtUnresolvedImport]import io.vertx.sqlclient.SqlClient;
[CtUnresolvedImport]import io.vertx.sqlclient.SqlConnectOptions;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import io.vertx.core.Vertx;
[CtUnresolvedImport]import io.vertx.sqlclient.Pool;
[CtUnresolvedImport]import io.vertx.sqlclient.Cursor;
[CtUnresolvedImport]import io.vertx.sqlclient.PoolOptions;
[CtUnresolvedImport]import io.vertx.sqlclient.PreparedStatement;
[CtUnresolvedImport]import io.vertx.sqlclient.Tuple;
[CtUnresolvedImport]import io.vertx.docgen.Source;
[CtUnresolvedImport]import io.vertx.sqlclient.SqlConnection;
[CtUnresolvedImport]import io.vertx.sqlclient.Row;
[CtUnresolvedImport]import io.vertx.sqlclient.RowSet;
[CtUnresolvedImport]import io.vertx.sqlclient.Transaction;
[CtImportImpl]import java.util.List;
[CtClassImpl][CtAnnotationImpl]@io.vertx.docgen.Source
public class SqlClientExamples {
    [CtMethodImpl]public [CtTypeReferenceImpl]void queries01([CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.SqlClient client) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]client.query([CtLiteralImpl]"SELECT * FROM users WHERE id='andy'", [CtLambdaImpl]([CtParameterImpl] ar) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar.succeeded()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]RowSet<[CtTypeReferenceImpl]io.vertx.sqlclient.Row> result = [CtInvocationImpl][CtVariableReadImpl]ar.result();
                [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Got " + [CtInvocationImpl][CtVariableReadImpl]result.size()) + [CtLiteralImpl]" rows ");
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Failure: " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ar.cause().getMessage());
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void queries02([CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.SqlClient client) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]client.preparedQuery([CtLiteralImpl]"SELECT * FROM users WHERE id=$1", [CtInvocationImpl][CtTypeAccessImpl]io.vertx.sqlclient.Tuple.of([CtLiteralImpl]"andy"), [CtLambdaImpl]([CtParameterImpl] ar) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar.succeeded()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]RowSet<[CtTypeReferenceImpl]io.vertx.sqlclient.Row> rows = [CtInvocationImpl][CtVariableReadImpl]ar.result();
                [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Got " + [CtInvocationImpl][CtVariableReadImpl]rows.size()) + [CtLiteralImpl]" rows ");
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Failure: " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ar.cause().getMessage());
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void queries03([CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.SqlClient client) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]client.preparedQuery([CtLiteralImpl]"SELECT first_name, last_name FROM users", [CtLambdaImpl]([CtParameterImpl] ar) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar.succeeded()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]RowSet<[CtTypeReferenceImpl]io.vertx.sqlclient.Row> rows = [CtInvocationImpl][CtVariableReadImpl]ar.result();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.vertx.sqlclient.Row row : [CtVariableReadImpl]rows) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"User " + [CtInvocationImpl][CtVariableReadImpl]row.getString([CtLiteralImpl]0)) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtVariableReadImpl]row.getString([CtLiteralImpl]1));
                }
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Failure: " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ar.cause().getMessage());
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void queries04([CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.SqlClient client) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]client.preparedQuery([CtLiteralImpl]"INSERT INTO users (first_name, last_name) VALUES ($1, $2)", [CtInvocationImpl][CtTypeAccessImpl]io.vertx.sqlclient.Tuple.of([CtLiteralImpl]"Andy", [CtLiteralImpl]"Guibert"), [CtLambdaImpl]([CtParameterImpl] ar) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar.succeeded()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]RowSet<[CtTypeReferenceImpl]io.vertx.sqlclient.Row> rows = [CtInvocationImpl][CtVariableReadImpl]ar.result();
                [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtInvocationImpl][CtVariableReadImpl]rows.rowCount());
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Failure: " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ar.cause().getMessage());
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void queries05([CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.Row row) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"User " + [CtInvocationImpl][CtVariableReadImpl]row.getString([CtLiteralImpl]0)) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtVariableReadImpl]row.getString([CtLiteralImpl]1));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void queries06([CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.Row row) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.lang.System.[CtFieldReferenceImpl]out.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"User " + [CtInvocationImpl][CtVariableReadImpl]row.getString([CtLiteralImpl]"first_name")) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtVariableReadImpl]row.getString([CtLiteralImpl]"last_name"));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void queries07([CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.Row row) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String firstName = [CtInvocationImpl][CtVariableReadImpl]row.getString([CtLiteralImpl]"first_name");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Boolean male = [CtInvocationImpl][CtVariableReadImpl]row.getBoolean([CtLiteralImpl]"male");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer age = [CtInvocationImpl][CtVariableReadImpl]row.getInteger([CtLiteralImpl]"age");
        [CtCommentImpl]// ...
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void queries08([CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.SqlClient client) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Add commands to the batch
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.vertx.sqlclient.Tuple> batch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]batch.add([CtInvocationImpl][CtTypeAccessImpl]io.vertx.sqlclient.Tuple.of([CtLiteralImpl]"julien", [CtLiteralImpl]"Julient Viet"));
        [CtInvocationImpl][CtVariableReadImpl]batch.add([CtInvocationImpl][CtTypeAccessImpl]io.vertx.sqlclient.Tuple.of([CtLiteralImpl]"emad", [CtLiteralImpl]"Emad Alblueshi"));
        [CtInvocationImpl][CtVariableReadImpl]batch.add([CtInvocationImpl][CtTypeAccessImpl]io.vertx.sqlclient.Tuple.of([CtLiteralImpl]"andy", [CtLiteralImpl]"Andy Guibert"));
        [CtInvocationImpl][CtCommentImpl]// Execute the prepared batch
        [CtVariableReadImpl]client.preparedBatch([CtLiteralImpl]"INSERT INTO USERS (id, name) VALUES ($1, $2)", [CtVariableReadImpl]batch, [CtLambdaImpl]([CtParameterImpl] res) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]res.succeeded()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// Process rows
                [CtTypeReferenceImpl]RowSet<[CtTypeReferenceImpl]io.vertx.sqlclient.Row> rows = [CtInvocationImpl][CtVariableReadImpl]res.result();
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Batch failed " + [CtInvocationImpl][CtVariableReadImpl]res.cause());
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void queries09([CtParameterImpl][CtTypeReferenceImpl]io.vertx.core.Vertx vertx, [CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.SqlConnectOptions connectOptions, [CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.PoolOptions poolOptions) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Enable prepare statements caching
        [CtVariableReadImpl]connectOptions.setCachePreparedStatements([CtLiteralImpl]true);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void usingConnections01([CtParameterImpl][CtTypeReferenceImpl]io.vertx.core.Vertx vertx, [CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.Pool pool) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]pool.getConnection([CtLambdaImpl]([CtParameterImpl] ar1) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar1.succeeded()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.vertx.sqlclient.SqlConnection connection = [CtInvocationImpl][CtVariableReadImpl]ar1.result();
                [CtInvocationImpl][CtVariableReadImpl]connection.query([CtLiteralImpl]"SELECT * FROM users WHERE id='andy'", [CtLambdaImpl]([CtParameterImpl] ar2) -> [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar1.succeeded()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]connection.query([CtLiteralImpl]"SELECT * FROM users WHERE id='julien'", [CtLambdaImpl]([CtParameterImpl] ar3) -> [CtBlockImpl]{
                            [CtInvocationImpl][CtCommentImpl]// Do something with rows and return the connection to the pool
                            [CtVariableReadImpl]connection.close();
                        });
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// Return the connection to the pool
                        [CtVariableReadImpl]connection.close();
                    }
                });
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void usingConnections02([CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.SqlConnection connection) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]connection.prepare([CtLiteralImpl]"SELECT * FROM users WHERE first_name LIKE $1", [CtLambdaImpl]([CtParameterImpl] ar1) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar1.succeeded()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.vertx.sqlclient.PreparedStatement pq = [CtInvocationImpl][CtVariableReadImpl]ar1.result();
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pq.query().execute([CtInvocationImpl][CtTypeAccessImpl]io.vertx.sqlclient.Tuple.of([CtLiteralImpl]"andy"), [CtLambdaImpl]([CtParameterImpl] ar2) -> [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar2.succeeded()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtCommentImpl]// All rows
                        [CtTypeReferenceImpl]RowSet<[CtTypeReferenceImpl]io.vertx.sqlclient.Row> rows = [CtInvocationImpl][CtVariableReadImpl]ar2.result();
                    }
                });
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void usingConnections03([CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.SqlConnection connection) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]connection.prepare([CtLiteralImpl]"INSERT INTO USERS (id, name) VALUES ($1, $2)", [CtLambdaImpl]([CtParameterImpl] ar1) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar1.succeeded()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.vertx.sqlclient.PreparedStatement prepared = [CtInvocationImpl][CtVariableReadImpl]ar1.result();
                [CtLocalVariableImpl][CtCommentImpl]// Create a query : bind parameters
                [CtTypeReferenceImpl]List<[CtTypeReferenceImpl]io.vertx.sqlclient.Tuple> batch = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList();
                [CtInvocationImpl][CtCommentImpl]// Add commands to the createBatch
                [CtVariableReadImpl]batch.add([CtInvocationImpl][CtTypeAccessImpl]io.vertx.sqlclient.Tuple.of([CtLiteralImpl]"julien", [CtLiteralImpl]"Julien Viet"));
                [CtInvocationImpl][CtVariableReadImpl]batch.add([CtInvocationImpl][CtTypeAccessImpl]io.vertx.sqlclient.Tuple.of([CtLiteralImpl]"emad", [CtLiteralImpl]"Emad Alblueshi"));
                [CtInvocationImpl][CtVariableReadImpl]batch.add([CtInvocationImpl][CtTypeAccessImpl]io.vertx.sqlclient.Tuple.of([CtLiteralImpl]"andy", [CtLiteralImpl]"Andy Guibert"));
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]prepared.query().batch([CtVariableReadImpl]batch, [CtLambdaImpl]([CtParameterImpl] res) -> [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]res.succeeded()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtCommentImpl]// Process rows
                        [CtTypeReferenceImpl]RowSet<[CtTypeReferenceImpl]io.vertx.sqlclient.Row> rows = [CtInvocationImpl][CtVariableReadImpl]res.result();
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Batch failed " + [CtInvocationImpl][CtVariableReadImpl]res.cause());
                    }
                });
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void transaction01([CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.Pool pool) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]pool.getConnection([CtLambdaImpl]([CtParameterImpl] res) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]res.succeeded()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// Transaction must use a connection
                [CtTypeReferenceImpl]io.vertx.sqlclient.SqlConnection conn = [CtInvocationImpl][CtVariableReadImpl]res.result();
                [CtLocalVariableImpl][CtCommentImpl]// Begin the transaction
                [CtTypeReferenceImpl]io.vertx.sqlclient.Transaction tx = [CtInvocationImpl][CtVariableReadImpl]conn.begin();
                [CtInvocationImpl][CtCommentImpl]// Various statements
                [CtVariableReadImpl]conn.query([CtLiteralImpl]"INSERT INTO Users (first_name,last_name) VALUES ('Julien','Viet')", [CtLambdaImpl]([CtParameterImpl] ar1) -> [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar1.succeeded()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]conn.query([CtLiteralImpl]"INSERT INTO Users (first_name,last_name) VALUES ('Emad','Alblueshi')", [CtLambdaImpl]([CtParameterImpl] ar2) -> [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar2.succeeded()) [CtBlockImpl]{
                                [CtInvocationImpl][CtCommentImpl]// Commit the transaction
                                [CtVariableReadImpl]tx.commit([CtLambdaImpl]([CtParameterImpl] ar3) -> [CtBlockImpl]{
                                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar3.succeeded()) [CtBlockImpl]{
                                        [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtLiteralImpl]"Transaction succeeded");
                                    } else [CtBlockImpl]{
                                        [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Transaction failed " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ar3.cause().getMessage());
                                    }
                                    [CtInvocationImpl][CtCommentImpl]// Return the connection to the pool
                                    [CtVariableReadImpl]conn.close();
                                });
                            } else [CtBlockImpl]{
                                [CtInvocationImpl][CtCommentImpl]// Return the connection to the pool
                                [CtVariableReadImpl]conn.close();
                            }
                        });
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// Return the connection to the pool
                        [CtVariableReadImpl]conn.close();
                    }
                });
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void transaction02([CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.Transaction tx) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]tx.abortHandler([CtLambdaImpl]([CtParameterImpl] v) -> [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtLiteralImpl]"Transaction failed => rollbacked");
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void transaction03([CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.Pool pool) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Acquire a transaction and begin the transaction
        [CtVariableReadImpl]pool.begin([CtLambdaImpl]([CtParameterImpl] res) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]res.succeeded()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// Get the transaction
                [CtTypeReferenceImpl]io.vertx.sqlclient.Transaction tx = [CtInvocationImpl][CtVariableReadImpl]res.result();
                [CtInvocationImpl][CtCommentImpl]// Various statements
                [CtVariableReadImpl]tx.query([CtLiteralImpl]"INSERT INTO Users (first_name,last_name) VALUES ('Julien','Viet')", [CtLambdaImpl]([CtParameterImpl] ar1) -> [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar1.succeeded()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]tx.query([CtLiteralImpl]"INSERT INTO Users (first_name,last_name) VALUES ('Emad','Alblueshi')", [CtLambdaImpl]([CtParameterImpl] ar2) -> [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar2.succeeded()) [CtBlockImpl]{
                                [CtInvocationImpl][CtCommentImpl]// Commit the transaction
                                [CtCommentImpl]// the connection will automatically return to the pool
                                [CtVariableReadImpl]tx.commit([CtLambdaImpl]([CtParameterImpl] ar3) -> [CtBlockImpl]{
                                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar3.succeeded()) [CtBlockImpl]{
                                        [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtLiteralImpl]"Transaction succeeded");
                                    } else [CtBlockImpl]{
                                        [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Transaction failed " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ar3.cause().getMessage());
                                    }
                                });
                            }
                        });
                    } else [CtBlockImpl]{
                        [CtCommentImpl]// No need to close connection as transaction will abort and be returned to the pool
                    }
                });
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void usingCursors01([CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.SqlConnection connection) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]connection.prepare([CtLiteralImpl]"SELECT * FROM users WHERE first_name LIKE $1", [CtLambdaImpl]([CtParameterImpl] ar1) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar1.succeeded()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.vertx.sqlclient.PreparedStatement pq = [CtInvocationImpl][CtVariableReadImpl]ar1.result();
                [CtLocalVariableImpl][CtCommentImpl]// Cursors require to run within a transaction
                [CtTypeReferenceImpl]io.vertx.sqlclient.Transaction tx = [CtInvocationImpl][CtVariableReadImpl]connection.begin();
                [CtLocalVariableImpl][CtCommentImpl]// Create a cursor
                [CtTypeReferenceImpl]io.vertx.sqlclient.Cursor cursor = [CtInvocationImpl][CtVariableReadImpl]pq.cursor([CtInvocationImpl][CtTypeAccessImpl]io.vertx.sqlclient.Tuple.of([CtLiteralImpl]"julien"));
                [CtInvocationImpl][CtCommentImpl]// Read 50 rows
                [CtVariableReadImpl]cursor.read([CtLiteralImpl]50, [CtLambdaImpl]([CtParameterImpl] ar2) -> [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar2.succeeded()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]RowSet<[CtTypeReferenceImpl]io.vertx.sqlclient.Row> rows = [CtInvocationImpl][CtVariableReadImpl]ar2.result();
                        [CtIfImpl][CtCommentImpl]// Check for more ?
                        if ([CtInvocationImpl][CtVariableReadImpl]cursor.hasMore()) [CtBlockImpl]{
                            [CtCommentImpl]// Repeat the process...
                        } else [CtBlockImpl]{
                            [CtInvocationImpl][CtCommentImpl]// No more rows - commit the transaction
                            [CtVariableReadImpl]tx.commit();
                        }
                    }
                });
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void usingCursors02([CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.Cursor cursor) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]cursor.read([CtLiteralImpl]50, [CtLambdaImpl]([CtParameterImpl] ar2) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar2.succeeded()) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Close the cursor
                [CtVariableReadImpl]cursor.close();
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void usingCursors03([CtParameterImpl][CtTypeReferenceImpl]io.vertx.sqlclient.SqlConnection connection) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]connection.prepare([CtLiteralImpl]"SELECT * FROM users WHERE first_name LIKE $1", [CtLambdaImpl]([CtParameterImpl] ar1) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ar1.succeeded()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]io.vertx.sqlclient.PreparedStatement pq = [CtInvocationImpl][CtVariableReadImpl]ar1.result();
                [CtLocalVariableImpl][CtCommentImpl]// Streams require to run within a transaction
                [CtTypeReferenceImpl]io.vertx.sqlclient.Transaction tx = [CtInvocationImpl][CtVariableReadImpl]connection.begin();
                [CtLocalVariableImpl][CtCommentImpl]// Fetch 50 rows at a time
                [CtTypeReferenceImpl]RowStream<[CtTypeReferenceImpl]io.vertx.sqlclient.Row> stream = [CtInvocationImpl][CtVariableReadImpl]pq.createStream([CtLiteralImpl]50, [CtInvocationImpl][CtTypeAccessImpl]io.vertx.sqlclient.Tuple.of([CtLiteralImpl]"julien"));
                [CtInvocationImpl][CtCommentImpl]// Use the stream
                [CtVariableReadImpl]stream.exceptionHandler([CtLambdaImpl]([CtParameterImpl] err) -> [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtBinaryOperatorImpl][CtLiteralImpl]"Error: " + [CtInvocationImpl][CtVariableReadImpl]err.getMessage());
                });
                [CtInvocationImpl][CtVariableReadImpl]stream.endHandler([CtLambdaImpl]([CtParameterImpl] v) -> [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]tx.commit();
                    [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtLiteralImpl]"End of stream");
                });
                [CtInvocationImpl][CtVariableReadImpl]stream.handler([CtLambdaImpl]([CtParameterImpl] row) -> [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]System.out.println([CtBinaryOperatorImpl][CtLiteralImpl]"User: " + [CtInvocationImpl][CtVariableReadImpl]row.getString([CtLiteralImpl]"last_name"));
                });
            }
        });
    }
}