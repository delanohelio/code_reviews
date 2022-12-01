[CompilationUnitImpl][CtPackageDeclarationImpl]package de.tum.in.www1.artemis.domain.exam;
[CtUnresolvedImport]import javax.persistence.Entity;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import javax.persistence.JoinTable;
[CtUnresolvedImport]import javax.persistence.ManyToOne;
[CtUnresolvedImport]import javax.persistence.JoinColumn;
[CtUnresolvedImport]import javax.persistence.OneToMany;
[CtUnresolvedImport]import de.tum.in.www1.artemis.domain.Course;
[CtUnresolvedImport]import de.tum.in.www1.artemis.domain.ExerciseGroup;
[CtUnresolvedImport]import javax.persistence.Column;
[CtUnresolvedImport]import javax.persistence.GenerationType;
[CtUnresolvedImport]import de.tum.in.www1.artemis.domain.User;
[CtImportImpl]import java.time.ZonedDateTime;
[CtUnresolvedImport]import de.tum.in.www1.artemis.domain.AbstractAuditingEntity;
[CtUnresolvedImport]import javax.persistence.Table;
[CtUnresolvedImport]import javax.persistence.GeneratedValue;
[CtUnresolvedImport]import javax.persistence.FetchType;
[CtImportImpl]import java.util.Objects;
[CtUnresolvedImport]import javax.persistence.ManyToMany;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import javax.persistence.Id;
[CtClassImpl][CtCommentImpl]// endregion
[CtCommentImpl]// -----------------------------------------------------------------------------------------------------------------
[CtAnnotationImpl]@javax.persistence.Entity
[CtAnnotationImpl]@javax.persistence.Table(name = [CtLiteralImpl]"EXAM")
public class Exam extends [CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.AbstractAuditingEntity {
    [CtConstructorImpl][CtCommentImpl]// region CONSTRUCTORS
    [CtCommentImpl]// -----------------------------------------------------------------------------------------------------------------
    [CtCommentImpl]// no arg constructor required for jpa
    public Exam() [CtBlockImpl]{
    }

    [CtConstructorImpl]public Exam([CtParameterImpl][CtTypeReferenceImpl]java.lang.Long id, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String title, [CtParameterImpl][CtTypeReferenceImpl]java.time.ZonedDateTime visibleDate, [CtParameterImpl][CtTypeReferenceImpl]java.time.ZonedDateTime startDate, [CtParameterImpl][CtTypeReferenceImpl]java.time.ZonedDateTime endDate, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String startText, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String endText, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String confirmationStartText, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String confirmationEndText, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer numberOfExerciseGroups, [CtParameterImpl][CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.Course course, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.ExerciseGroup> exerciseGroups, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.exam.StudentExam> studentExams, [CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.User> registeredUsers) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.id = [CtVariableReadImpl]id;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.title = [CtVariableReadImpl]title;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.visibleDate = [CtVariableReadImpl]visibleDate;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.startDate = [CtVariableReadImpl]startDate;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.endDate = [CtVariableReadImpl]endDate;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.startText = [CtVariableReadImpl]startText;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.endText = [CtVariableReadImpl]endText;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.confirmationStartText = [CtVariableReadImpl]confirmationStartText;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.confirmationEndText = [CtVariableReadImpl]confirmationEndText;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.numberOfExerciseGroups = [CtVariableReadImpl]numberOfExerciseGroups;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.course = [CtVariableReadImpl]course;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.exerciseGroups = [CtVariableReadImpl]exerciseGroups;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.studentExams = [CtVariableReadImpl]studentExams;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.registeredUsers = [CtVariableReadImpl]registeredUsers;
    }

    [CtFieldImpl][CtCommentImpl]// -----------------------------------------------------------------------------------------------------------------
    [CtCommentImpl]// endregion
    [CtCommentImpl]// region BASIC PROPERTIES
    [CtCommentImpl]// -----------------------------------------------------------------------------------------------------------------
    [CtAnnotationImpl]@javax.persistence.Id
    [CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"ID")
    [CtAnnotationImpl]@javax.persistence.GeneratedValue(strategy = [CtFieldReadImpl]javax.persistence.GenerationType.IDENTITY)
    private [CtTypeReferenceImpl]java.lang.Long id;

    [CtFieldImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"TITLE", unique = [CtLiteralImpl]true, nullable = [CtLiteralImpl]false)
    private [CtTypeReferenceImpl]java.lang.String title;

    [CtFieldImpl][CtJavaDocImpl]/**
     * student can see the exam in the UI from {@link #visibleDate} date onwards
     */
    [CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"VISIBLE_DATE")
    private [CtTypeReferenceImpl]java.time.ZonedDateTime visibleDate;

    [CtFieldImpl][CtJavaDocImpl]/**
     * student can start working on exam from {@link #startDate}
     */
    [CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"START_DATE")
    private [CtTypeReferenceImpl]java.time.ZonedDateTime startDate;

    [CtFieldImpl][CtJavaDocImpl]/**
     * student can work on exam until {@link #endDate}
     */
    [CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"END_DATE")
    private [CtTypeReferenceImpl]java.time.ZonedDateTime endDate;

    [CtFieldImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"START_TEXT")
    private [CtTypeReferenceImpl]java.lang.String startText;

    [CtFieldImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"END_TEXT")
    private [CtTypeReferenceImpl]java.lang.String endText;

    [CtFieldImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"CONFIRMATION_START_TEXT")
    private [CtTypeReferenceImpl]java.lang.String confirmationStartText;

    [CtFieldImpl][CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"CONFIRMATION_END_TEXT")
    private [CtTypeReferenceImpl]java.lang.String confirmationEndText;

    [CtFieldImpl][CtJavaDocImpl]/**
     * From all exercise groups connected to the exam, {@link #numberOfExerciseGroups} are randomly
     * chosen when generating the specific exam for the {@link #registeredUsers}
     */
    [CtAnnotationImpl]@javax.persistence.Column(name = [CtLiteralImpl]"NUMBER_OF_EXERCISE_GROUPS")
    private [CtTypeReferenceImpl]java.lang.Integer numberOfExerciseGroups;

    [CtFieldImpl][CtCommentImpl]// -----------------------------------------------------------------------------------------------------------------
    [CtCommentImpl]// endregion
    [CtCommentImpl]// region RELATIONSHIPS
    [CtCommentImpl]// -----------------------------------------------------------------------------------------------------------------
    [CtAnnotationImpl]@javax.persistence.ManyToOne(fetch = [CtFieldReadImpl]javax.persistence.FetchType.LAZY)
    [CtAnnotationImpl]@javax.persistence.JoinColumn(name = [CtLiteralImpl]"COURSE_ID")
    private [CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.Course course;

    [CtMethodImpl]public [CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.Course getCourse() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]course;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setCourse([CtParameterImpl][CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.Course course) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.course != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.course.removeExam([CtThisAccessImpl]this);
        }
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.course = [CtVariableReadImpl]course;
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]course.getExams().contains([CtThisAccessImpl]this)) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]course.getExams().add([CtThisAccessImpl]this);
        }
    }

    [CtFieldImpl][CtCommentImpl]// -----------------------------------------------------------------------------------------------------------------
    [CtAnnotationImpl]@javax.persistence.OneToMany(mappedBy = [CtLiteralImpl]"exam")
    private [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.ExerciseGroup> exerciseGroups = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.ExerciseGroup> getExerciseGroups() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]exerciseGroups;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setExerciseGroups([CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.ExerciseGroup> exerciseGroups) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.exerciseGroups = [CtVariableReadImpl]exerciseGroups;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addExerciseGroup([CtParameterImpl][CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.ExerciseGroup exerciseGroup) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.exerciseGroups.add([CtVariableReadImpl]exerciseGroup);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exerciseGroup.getExam() != [CtThisAccessImpl]this) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]exerciseGroup.setExam([CtThisAccessImpl]this);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeExerciseGroup([CtParameterImpl][CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.ExerciseGroup exerciseGroup) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.exerciseGroups.remove([CtVariableReadImpl]exerciseGroup);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]exerciseGroup.getExam() == [CtThisAccessImpl]this) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]exerciseGroup.setExam([CtLiteralImpl]null);
        }
    }

    [CtFieldImpl][CtCommentImpl]// -----------------------------------------------------------------------------------------------------------------
    [CtAnnotationImpl]@javax.persistence.OneToMany(mappedBy = [CtLiteralImpl]"exam")
    private [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.exam.StudentExam> studentExams = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.exam.StudentExam> getStudentExams() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]studentExams;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setStudentExams([CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.exam.StudentExam> studentExams) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.studentExams = [CtVariableReadImpl]studentExams;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addStudentExam([CtParameterImpl][CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.exam.StudentExam studentExam) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.studentExams.add([CtVariableReadImpl]studentExam);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]studentExam.getExam() != [CtThisAccessImpl]this) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]studentExam.setExam([CtThisAccessImpl]this);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeStudentExam([CtParameterImpl][CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.exam.StudentExam studentExam) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.studentExams.remove([CtVariableReadImpl]studentExam);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]studentExam.getExam() == [CtThisAccessImpl]this) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]studentExam.setExam([CtLiteralImpl]null);
        }
    }

    [CtFieldImpl][CtCommentImpl]// -----------------------------------------------------------------------------------------------------------------
    [CtAnnotationImpl]@javax.persistence.ManyToMany
    [CtAnnotationImpl]@javax.persistence.JoinTable(name = [CtLiteralImpl]"EXAM_JHI_USER", joinColumns = [CtAnnotationImpl]@javax.persistence.JoinColumn(name = [CtLiteralImpl]"EXAM_ID", referencedColumnName = [CtLiteralImpl]"ID"), inverseJoinColumns = [CtAnnotationImpl]@javax.persistence.JoinColumn(name = [CtLiteralImpl]"STUDENT_ID", referencedColumnName = [CtLiteralImpl]"ID"))
    private [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.User> registeredUsers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.User> getRegisteredUsers() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]registeredUsers;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setRegisteredUsers([CtParameterImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.User> registeredUsers) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.registeredUsers = [CtVariableReadImpl]registeredUsers;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addUser([CtParameterImpl][CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.User user) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.registeredUsers.add([CtVariableReadImpl]user);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]user.getExams().remove([CtThisAccessImpl]this);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeUser([CtParameterImpl][CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.User user) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.registeredUsers.remove([CtVariableReadImpl]user);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]user.getExams().remove([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtCommentImpl]// -----------------------------------------------------------------------------------------------------------------
    [CtCommentImpl]// endregion
    [CtCommentImpl]// region SIMPLE GETTERS AND SETTERS
    [CtCommentImpl]// -----------------------------------------------------------------------------------------------------------------
    public [CtTypeReferenceImpl]java.lang.Long getId() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]id;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setId([CtParameterImpl][CtTypeReferenceImpl]java.lang.Long id) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.id = [CtVariableReadImpl]id;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getTitle() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]title;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setTitle([CtParameterImpl][CtTypeReferenceImpl]java.lang.String title) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.title = [CtVariableReadImpl]title;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.time.ZonedDateTime getVisibleDate() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]visibleDate;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setVisibleDate([CtParameterImpl][CtTypeReferenceImpl]java.time.ZonedDateTime visibleDate) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.visibleDate = [CtVariableReadImpl]visibleDate;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.time.ZonedDateTime getStartDate() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]startDate;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setStartDate([CtParameterImpl][CtTypeReferenceImpl]java.time.ZonedDateTime startDate) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.startDate = [CtVariableReadImpl]startDate;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.time.ZonedDateTime getEndDate() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]endDate;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setEndDate([CtParameterImpl][CtTypeReferenceImpl]java.time.ZonedDateTime endDate) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.endDate = [CtVariableReadImpl]endDate;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getStartText() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]startText;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setStartText([CtParameterImpl][CtTypeReferenceImpl]java.lang.String startText) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.startText = [CtVariableReadImpl]startText;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getEndText() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]endText;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setEndText([CtParameterImpl][CtTypeReferenceImpl]java.lang.String endText) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.endText = [CtVariableReadImpl]endText;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getConfirmationStartText() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]confirmationStartText;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setConfirmationStartText([CtParameterImpl][CtTypeReferenceImpl]java.lang.String confirmationStartText) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.confirmationStartText = [CtVariableReadImpl]confirmationStartText;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getConfirmationEndText() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]confirmationEndText;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setConfirmationEndText([CtParameterImpl][CtTypeReferenceImpl]java.lang.String confirmationEndText) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.confirmationEndText = [CtVariableReadImpl]confirmationEndText;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Integer getNumberOfExerciseGroups() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]numberOfExerciseGroups;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setNumberOfExerciseGroups([CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer numberOfExerciseGroups) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.numberOfExerciseGroups = [CtVariableReadImpl]numberOfExerciseGroups;
    }

    [CtMethodImpl][CtCommentImpl]// endregion
    [CtCommentImpl]// -----------------------------------------------------------------------------------------------------------------
    [CtCommentImpl]// region HASHCODE AND EQUAL
    [CtCommentImpl]// -----------------------------------------------------------------------------------------------------------------
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean equals([CtParameterImpl][CtTypeReferenceImpl]java.lang.Object o) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtThisAccessImpl]this == [CtVariableReadImpl]o)[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]true;

        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]o == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl]getClass() != [CtInvocationImpl][CtVariableReadImpl]o.getClass()))[CtBlockImpl]
            [CtReturnImpl]return [CtLiteralImpl]false;

        [CtLocalVariableImpl][CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.exam.Exam exam = [CtVariableReadImpl](([CtTypeReferenceImpl]de.tum.in.www1.artemis.domain.exam.Exam) (o));
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.equals([CtFieldReadImpl]title, [CtFieldReadImpl][CtVariableReadImpl]exam.title);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int hashCode() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Objects.hash([CtFieldReadImpl]title);
    }
}