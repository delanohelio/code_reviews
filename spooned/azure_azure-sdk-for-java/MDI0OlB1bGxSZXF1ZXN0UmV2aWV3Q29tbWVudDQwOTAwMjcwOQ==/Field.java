[CompilationUnitImpl][CtPackageDeclarationImpl]package com.azure.search.documents.models;
[CtImportImpl]import com.fasterxml.jackson.annotation.JsonProperty;
[CtImportImpl]import com.fasterxml.jackson.annotation.JsonIgnore;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.azure.core.annotation.Fluent;
[CtClassImpl][CtJavaDocImpl]/**
 * Represents a field in an index definition, which describes the name, data
 * type, and search behavior of a field.
 */
[CtAnnotationImpl]@com.azure.core.annotation.Fluent
public final class Field {
    [CtFieldImpl][CtCommentImpl]/* The name of the field, which must be unique within the fields collection
    of the index or parent field.
     */
    [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty(value = [CtLiteralImpl]"name", required = [CtLiteralImpl]true)
    private [CtTypeReferenceImpl]java.lang.String name;

    [CtFieldImpl][CtCommentImpl]/* The data type of the field. Possible values include: 'Edm.String',
    'Edm.Int32', 'Edm.Int64', 'Edm.Double', 'Edm.Boolean',
    'Edm.DateTimeOffset', 'Edm.GeographyPoint', 'Edm.ComplexType'
     */
    [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty(value = [CtLiteralImpl]"type", required = [CtLiteralImpl]true)
    private [CtTypeReferenceImpl]com.azure.search.documents.models.DataType type;

    [CtFieldImpl][CtCommentImpl]/* A value indicating whether the field uniquely identifies documents in
    the index. Exactly one top-level field in each index must be chosen as
    the key field and it must be of type Edm.String. Key fields can be used
    to look up documents directly and update or delete specific documents.
    Default is false for simple fields and null for complex fields.
     */
    [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"key")
    private [CtTypeReferenceImpl]java.lang.Boolean key;

    [CtFieldImpl][CtCommentImpl]/* A value indicating whether the field can be returned in a search result.
    You can disable this option if you want to use a field (for example,
    margin) as a filter, sorting, or scoring mechanism but do not want the
    field to be visible to the end user. This property must be true for key
    fields, and it must be null for complex fields. This property can be
    changed on existing fields. Enabling this property does not cause any
    increase in index storage requirements. Default is true for simple
    fields and null for complex fields.
     */
    [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"retrievable")
    private [CtTypeReferenceImpl]java.lang.Boolean retrievable;

    [CtFieldImpl][CtCommentImpl]/* A value indicating whether the field is full-text searchable. This means
    it will undergo analysis such as word-breaking during indexing. If you
    set a searchable field to a value like "sunny day", internally it will
    be split into the individual tokens "sunny" and "day". This enables
    full-text searches for these terms. Fields of type Edm.String or
    Collection(Edm.String) are searchable by default. This property must be
    false for simple fields of other non-string data types, and it must be
    null for complex fields. Note: searchable fields consume extra space in
    your index since Azure Cognitive Search will store an additional
    tokenized version of the field value for full-text searches. If you want
    to save space in your index and you don't need a field to be included in
    searches, set searchable to false.
     */
    [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"searchable")
    private [CtTypeReferenceImpl]java.lang.Boolean searchable;

    [CtFieldImpl][CtCommentImpl]/* A value indicating whether to enable the field to be referenced in
    $filter queries. filterable differs from searchable in how strings are
    handled. Fields of type Edm.String or Collection(Edm.String) that are
    filterable do not undergo word-breaking, so comparisons are for exact
    matches only. For example, if you set such a field f to "sunny day",
    $filter=f eq 'sunny' will find no matches, but $filter=f eq 'sunny day'
    will. This property must be null for complex fields. Default is true for
    simple fields and null for complex fields.
     */
    [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"filterable")
    private [CtTypeReferenceImpl]java.lang.Boolean filterable;

    [CtFieldImpl][CtCommentImpl]/* A value indicating whether to enable the field to be referenced in
    $orderby expressions. By default Azure Cognitive Search sorts results by
    score, but in many experiences users will want to sort by fields in the
    documents. A simple field can be sortable only if it is single-valued
    (it has a single value in the scope of the parent document). Simple
    collection fields cannot be sortable, since they are multi-valued.
    Simple sub-fields of complex collections are also multi-valued, and
    therefore cannot be sortable. This is true whether it's an immediate
    parent field, or an ancestor field, that's the complex collection.
    Complex fields cannot be sortable and the sortable property must be null
    for such fields. The default for sortable is true for single-valued
    simple fields, false for multi-valued simple fields, and null for
    complex fields.
     */
    [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"sortable")
    private [CtTypeReferenceImpl]java.lang.Boolean sortable;

    [CtFieldImpl][CtCommentImpl]/* A value indicating whether to enable the field to be referenced in facet
    queries. Typically used in a presentation of search results that
    includes hit count by category (for example, search for digital cameras
    and see hits by brand, by megapixels, by price, and so on). This
    property must be null for complex fields. Fields of type
    Edm.GeographyPoint or Collection(Edm.GeographyPoint) cannot be
    facetable. Default is true for all other simple fields.
     */
    [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"facetable")
    private [CtTypeReferenceImpl]java.lang.Boolean facetable;

    [CtFieldImpl][CtCommentImpl]/* The name of the analyzer to use for the field. This option can be used
    only with searchable fields and it can't be set together with either
    searchAnalyzer or indexAnalyzer. Once the analyzer is chosen, it cannot
    be changed for the field. Must be null for complex fields. Possible
    values include: 'ArMicrosoft', 'ArLucene', 'HyLucene', 'BnMicrosoft',
    'EuLucene', 'BgMicrosoft', 'BgLucene', 'CaMicrosoft', 'CaLucene',
    'ZhHansMicrosoft', 'ZhHansLucene', 'ZhHantMicrosoft', 'ZhHantLucene',
    'HrMicrosoft', 'CsMicrosoft', 'CsLucene', 'DaMicrosoft', 'DaLucene',
    'NlMicrosoft', 'NlLucene', 'EnMicrosoft', 'EnLucene', 'EtMicrosoft',
    'FiMicrosoft', 'FiLucene', 'FrMicrosoft', 'FrLucene', 'GlLucene',
    'DeMicrosoft', 'DeLucene', 'ElMicrosoft', 'ElLucene', 'GuMicrosoft',
    'HeMicrosoft', 'HiMicrosoft', 'HiLucene', 'HuMicrosoft', 'HuLucene',
    'IsMicrosoft', 'IdMicrosoft', 'IdLucene', 'GaLucene', 'ItMicrosoft',
    'ItLucene', 'JaMicrosoft', 'JaLucene', 'KnMicrosoft', 'KoMicrosoft',
    'KoLucene', 'LvMicrosoft', 'LvLucene', 'LtMicrosoft', 'MlMicrosoft',
    'MsMicrosoft', 'MrMicrosoft', 'NbMicrosoft', 'NoLucene', 'FaLucene',
    'PlMicrosoft', 'PlLucene', 'PtBrMicrosoft', 'PtBrLucene',
    'PtPtMicrosoft', 'PtPtLucene', 'PaMicrosoft', 'RoMicrosoft', 'RoLucene',
    'RuMicrosoft', 'RuLucene', 'SrCyrillicMicrosoft', 'SrLatinMicrosoft',
    'SkMicrosoft', 'SlMicrosoft', 'EsMicrosoft', 'EsLucene', 'SvMicrosoft',
    'SvLucene', 'TaMicrosoft', 'TeMicrosoft', 'ThMicrosoft', 'ThLucene',
    'TrMicrosoft', 'TrLucene', 'UkMicrosoft', 'UrMicrosoft', 'ViMicrosoft',
    'StandardLucene', 'StandardAsciiFoldingLucene', 'Keyword', 'Pattern',
    'Simple', 'Stop', 'Whitespace'
     */
    [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"analyzer")
    private [CtTypeReferenceImpl]com.azure.search.documents.models.AnalyzerName analyzer;

    [CtFieldImpl][CtCommentImpl]/* The name of the analyzer used at search time for the field. This option
    can be used only with searchable fields. It must be set together with
    indexAnalyzer and it cannot be set together with the analyzer option.
    This property cannot be set to the name of a language analyzer; use the
    analyzer property instead if you need a language analyzer. This analyzer
    can be updated on an existing field. Must be null for complex fields.
    Possible values include: 'ArMicrosoft', 'ArLucene', 'HyLucene',
    'BnMicrosoft', 'EuLucene', 'BgMicrosoft', 'BgLucene', 'CaMicrosoft',
    'CaLucene', 'ZhHansMicrosoft', 'ZhHansLucene', 'ZhHantMicrosoft',
    'ZhHantLucene', 'HrMicrosoft', 'CsMicrosoft', 'CsLucene', 'DaMicrosoft',
    'DaLucene', 'NlMicrosoft', 'NlLucene', 'EnMicrosoft', 'EnLucene',
    'EtMicrosoft', 'FiMicrosoft', 'FiLucene', 'FrMicrosoft', 'FrLucene',
    'GlLucene', 'DeMicrosoft', 'DeLucene', 'ElMicrosoft', 'ElLucene',
    'GuMicrosoft', 'HeMicrosoft', 'HiMicrosoft', 'HiLucene', 'HuMicrosoft',
    'HuLucene', 'IsMicrosoft', 'IdMicrosoft', 'IdLucene', 'GaLucene',
    'ItMicrosoft', 'ItLucene', 'JaMicrosoft', 'JaLucene', 'KnMicrosoft',
    'KoMicrosoft', 'KoLucene', 'LvMicrosoft', 'LvLucene', 'LtMicrosoft',
    'MlMicrosoft', 'MsMicrosoft', 'MrMicrosoft', 'NbMicrosoft', 'NoLucene',
    'FaLucene', 'PlMicrosoft', 'PlLucene', 'PtBrMicrosoft', 'PtBrLucene',
    'PtPtMicrosoft', 'PtPtLucene', 'PaMicrosoft', 'RoMicrosoft', 'RoLucene',
    'RuMicrosoft', 'RuLucene', 'SrCyrillicMicrosoft', 'SrLatinMicrosoft',
    'SkMicrosoft', 'SlMicrosoft', 'EsMicrosoft', 'EsLucene', 'SvMicrosoft',
    'SvLucene', 'TaMicrosoft', 'TeMicrosoft', 'ThMicrosoft', 'ThLucene',
    'TrMicrosoft', 'TrLucene', 'UkMicrosoft', 'UrMicrosoft', 'ViMicrosoft',
    'StandardLucene', 'StandardAsciiFoldingLucene', 'Keyword', 'Pattern',
    'Simple', 'Stop', 'Whitespace'
     */
    [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"searchAnalyzer")
    private [CtTypeReferenceImpl]com.azure.search.documents.models.AnalyzerName searchAnalyzer;

    [CtFieldImpl][CtCommentImpl]/* The name of the analyzer used at indexing time for the field. This
    option can be used only with searchable fields. It must be set together
    with searchAnalyzer and it cannot be set together with the analyzer
    option.  This property cannot be set to the name of a language analyzer;
    use the analyzer property instead if you need a language analyzer. Once
    the analyzer is chosen, it cannot be changed for the field. Must be null
    for complex fields. Possible values include: 'ArMicrosoft', 'ArLucene',
    'HyLucene', 'BnMicrosoft', 'EuLucene', 'BgMicrosoft', 'BgLucene',
    'CaMicrosoft', 'CaLucene', 'ZhHansMicrosoft', 'ZhHansLucene',
    'ZhHantMicrosoft', 'ZhHantLucene', 'HrMicrosoft', 'CsMicrosoft',
    'CsLucene', 'DaMicrosoft', 'DaLucene', 'NlMicrosoft', 'NlLucene',
    'EnMicrosoft', 'EnLucene', 'EtMicrosoft', 'FiMicrosoft', 'FiLucene',
    'FrMicrosoft', 'FrLucene', 'GlLucene', 'DeMicrosoft', 'DeLucene',
    'ElMicrosoft', 'ElLucene', 'GuMicrosoft', 'HeMicrosoft', 'HiMicrosoft',
    'HiLucene', 'HuMicrosoft', 'HuLucene', 'IsMicrosoft', 'IdMicrosoft',
    'IdLucene', 'GaLucene', 'ItMicrosoft', 'ItLucene', 'JaMicrosoft',
    'JaLucene', 'KnMicrosoft', 'KoMicrosoft', 'KoLucene', 'LvMicrosoft',
    'LvLucene', 'LtMicrosoft', 'MlMicrosoft', 'MsMicrosoft', 'MrMicrosoft',
    'NbMicrosoft', 'NoLucene', 'FaLucene', 'PlMicrosoft', 'PlLucene',
    'PtBrMicrosoft', 'PtBrLucene', 'PtPtMicrosoft', 'PtPtLucene',
    'PaMicrosoft', 'RoMicrosoft', 'RoLucene', 'RuMicrosoft', 'RuLucene',
    'SrCyrillicMicrosoft', 'SrLatinMicrosoft', 'SkMicrosoft', 'SlMicrosoft',
    'EsMicrosoft', 'EsLucene', 'SvMicrosoft', 'SvLucene', 'TaMicrosoft',
    'TeMicrosoft', 'ThMicrosoft', 'ThLucene', 'TrMicrosoft', 'TrLucene',
    'UkMicrosoft', 'UrMicrosoft', 'ViMicrosoft', 'StandardLucene',
    'StandardAsciiFoldingLucene', 'Keyword', 'Pattern', 'Simple', 'Stop',
    'Whitespace'
     */
    [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"indexAnalyzer")
    private [CtTypeReferenceImpl]com.azure.search.documents.models.AnalyzerName indexAnalyzer;

    [CtFieldImpl][CtCommentImpl]/* A list of the names of synonym maps to associate with this field. This
    option can be used only with searchable fields. Currently only one
    synonym map per field is supported. Assigning a synonym map to a field
    ensures that query terms targeting that field are expanded at query-time
    using the rules in the synonym map. This attribute can be changed on
    existing fields. Must be null or an empty collection for complex fields.
     */
    [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"synonymMaps")
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> synonymMaps;

    [CtFieldImpl][CtCommentImpl]/* A list of sub-fields if this is a field of type Edm.ComplexType or
    Collection(Edm.ComplexType). Must be null or empty for simple fields.
     */
    [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonProperty([CtLiteralImpl]"fields")
    private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.azure.search.documents.models.Field> fields;

    [CtFieldImpl][CtCommentImpl]/* A value indicating whether the field will be returned in a search
    result. This property must be false for key fields, and must be null for
    complex fields. You can hide a field from search results if you want to
    use it only as a filter, for sorting, or for scoring. This property can
    also be changed on existing fields and enabling it does not cause an
    increase in index storage requirements.
     */
    [CtAnnotationImpl]@com.fasterxml.jackson.annotation.JsonIgnore
    private [CtTypeReferenceImpl]java.lang.Boolean hidden;

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the name property: The name of the field, which must be unique
     * within the fields collection of the index or parent field.
     *
     * @return the name value.
     */
    public [CtTypeReferenceImpl]java.lang.String getName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.name;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the name property: The name of the field, which must be unique
     * within the fields collection of the index or parent field.
     *
     * @param name
     * 		the name value to set.
     * @return the Field object itself.
     */
    public [CtTypeReferenceImpl]com.azure.search.documents.models.Field setName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]name;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the type property: The data type of the field. Possible values
     * include: 'Edm.String', 'Edm.Int32', 'Edm.Int64', 'Edm.Double',
     * 'Edm.Boolean', 'Edm.DateTimeOffset', 'Edm.GeographyPoint',
     * 'Edm.ComplexType'.
     *
     * @return the type value.
     */
    public [CtTypeReferenceImpl]com.azure.search.documents.models.DataType getType() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.type;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the type property: The data type of the field. Possible values
     * include: 'Edm.String', 'Edm.Int32', 'Edm.Int64', 'Edm.Double',
     * 'Edm.Boolean', 'Edm.DateTimeOffset', 'Edm.GeographyPoint',
     * 'Edm.ComplexType'.
     *
     * @param type
     * 		the type value to set.
     * @return the Field object itself.
     */
    public [CtTypeReferenceImpl]com.azure.search.documents.models.Field setType([CtParameterImpl][CtTypeReferenceImpl]com.azure.search.documents.models.DataType type) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.type = [CtVariableReadImpl]type;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the key property: A value indicating whether the field uniquely
     * identifies documents in the index. Exactly one top-level field in each
     * index must be chosen as the key field and it must be of type Edm.String.
     * Key fields can be used to look up documents directly and update or
     * delete specific documents. Default is false for simple fields and null
     * for complex fields.
     *
     * @return the key value.
     */
    public [CtTypeReferenceImpl]java.lang.Boolean isKey() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.key;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the key property: A value indicating whether the field uniquely
     * identifies documents in the index. Exactly one top-level field in each
     * index must be chosen as the key field and it must be of type Edm.String.
     * Key fields can be used to look up documents directly and update or
     * delete specific documents. Default is false for simple fields and null
     * for complex fields.
     *
     * @param key
     * 		the key value to set.
     * @return the Field object itself.
     */
    public [CtTypeReferenceImpl]com.azure.search.documents.models.Field setKey([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean key) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.key = [CtVariableReadImpl]key;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the retrievable property: A value indicating whether the field can
     * be returned in a search result. You can disable this option if you want
     * to use a field (for example, margin) as a filter, sorting, or scoring
     * mechanism but do not want the field to be visible to the end user. This
     * property must be true for key fields, and it must be null for complex
     * fields. This property can be changed on existing fields. Enabling this
     * property does not cause any increase in index storage requirements.
     * Default is true for simple fields and null for complex fields.
     *
     * @return the retrievable value.
     */
    private [CtTypeReferenceImpl]java.lang.Boolean isRetrievable() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.retrievable;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the retrievable property: A value indicating whether the field can
     * be returned in a search result. You can disable this option if you want
     * to use a field (for example, margin) as a filter, sorting, or scoring
     * mechanism but do not want the field to be visible to the end user. This
     * property must be true for key fields, and it must be null for complex
     * fields. This property can be changed on existing fields. Enabling this
     * property does not cause any increase in index storage requirements.
     * Default is true for simple fields and null for complex fields.
     *
     * @param retrievable
     * 		the retrievable value to set.
     * @return the Field object itself.
     */
    private [CtTypeReferenceImpl]com.azure.search.documents.models.Field setRetrievable([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean retrievable) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.retrievable = [CtVariableReadImpl]retrievable;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the searchable property: A value indicating whether the field is
     * full-text searchable. This means it will undergo analysis such as
     * word-breaking during indexing. If you set a searchable field to a value
     * like "sunny day", internally it will be split into the individual tokens
     * "sunny" and "day". This enables full-text searches for these terms.
     * Fields of type Edm.String or Collection(Edm.String) are searchable by
     * default. This property must be false for simple fields of other
     * non-string data types, and it must be null for complex fields. Note:
     * searchable fields consume extra space in your index since Azure
     * Cognitive Search will store an additional tokenized version of the field
     * value for full-text searches. If you want to save space in your index
     * and you don't need a field to be included in searches, set searchable to
     * false.
     *
     * @return the searchable value.
     */
    public [CtTypeReferenceImpl]java.lang.Boolean isSearchable() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.searchable;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the searchable property: A value indicating whether the field is
     * full-text searchable. This means it will undergo analysis such as
     * word-breaking during indexing. If you set a searchable field to a value
     * like "sunny day", internally it will be split into the individual tokens
     * "sunny" and "day". This enables full-text searches for these terms.
     * Fields of type Edm.String or Collection(Edm.String) are searchable by
     * default. This property must be false for simple fields of other
     * non-string data types, and it must be null for complex fields. Note:
     * searchable fields consume extra space in your index since Azure
     * Cognitive Search will store an additional tokenized version of the field
     * value for full-text searches. If you want to save space in your index
     * and you don't need a field to be included in searches, set searchable to
     * false.
     *
     * @param searchable
     * 		the searchable value to set.
     * @return the Field object itself.
     */
    public [CtTypeReferenceImpl]com.azure.search.documents.models.Field setSearchable([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean searchable) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.searchable = [CtVariableReadImpl]searchable;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the filterable property: A value indicating whether to enable the
     * field to be referenced in $filter queries. filterable differs from
     * searchable in how strings are handled. Fields of type Edm.String or
     * Collection(Edm.String) that are filterable do not undergo word-breaking,
     * so comparisons are for exact matches only. For example, if you set such
     * a field f to "sunny day", $filter=f eq 'sunny' will find no matches, but
     * $filter=f eq 'sunny day' will. This property must be null for complex
     * fields. Default is true for simple fields and null for complex fields.
     *
     * @return the filterable value.
     */
    public [CtTypeReferenceImpl]java.lang.Boolean isFilterable() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.filterable;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the filterable property: A value indicating whether to enable the
     * field to be referenced in $filter queries. filterable differs from
     * searchable in how strings are handled. Fields of type Edm.String or
     * Collection(Edm.String) that are filterable do not undergo word-breaking,
     * so comparisons are for exact matches only. For example, if you set such
     * a field f to "sunny day", $filter=f eq 'sunny' will find no matches, but
     * $filter=f eq 'sunny day' will. This property must be null for complex
     * fields. Default is true for simple fields and null for complex fields.
     *
     * @param filterable
     * 		the filterable value to set.
     * @return the Field object itself.
     */
    public [CtTypeReferenceImpl]com.azure.search.documents.models.Field setFilterable([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean filterable) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.filterable = [CtVariableReadImpl]filterable;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the sortable property: A value indicating whether to enable the
     * field to be referenced in $orderby expressions. By default Azure
     * Cognitive Search sorts results by score, but in many experiences users
     * will want to sort by fields in the documents. A simple field can be
     * sortable only if it is single-valued (it has a single value in the scope
     * of the parent document). Simple collection fields cannot be sortable,
     * since they are multi-valued. Simple sub-fields of complex collections
     * are also multi-valued, and therefore cannot be sortable. This is true
     * whether it's an immediate parent field, or an ancestor field, that's the
     * complex collection. Complex fields cannot be sortable and the sortable
     * property must be null for such fields. The default for sortable is true
     * for single-valued simple fields, false for multi-valued simple fields,
     * and null for complex fields.
     *
     * @return the sortable value.
     */
    public [CtTypeReferenceImpl]java.lang.Boolean isSortable() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.sortable;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the sortable property: A value indicating whether to enable the
     * field to be referenced in $orderby expressions. By default Azure
     * Cognitive Search sorts results by score, but in many experiences users
     * will want to sort by fields in the documents. A simple field can be
     * sortable only if it is single-valued (it has a single value in the scope
     * of the parent document). Simple collection fields cannot be sortable,
     * since they are multi-valued. Simple sub-fields of complex collections
     * are also multi-valued, and therefore cannot be sortable. This is true
     * whether it's an immediate parent field, or an ancestor field, that's the
     * complex collection. Complex fields cannot be sortable and the sortable
     * property must be null for such fields. The default for sortable is true
     * for single-valued simple fields, false for multi-valued simple fields,
     * and null for complex fields.
     *
     * @param sortable
     * 		the sortable value to set.
     * @return the Field object itself.
     */
    public [CtTypeReferenceImpl]com.azure.search.documents.models.Field setSortable([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean sortable) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sortable = [CtVariableReadImpl]sortable;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the facetable property: A value indicating whether to enable the
     * field to be referenced in facet queries. Typically used in a
     * presentation of search results that includes hit count by category (for
     * example, search for digital cameras and see hits by brand, by
     * megapixels, by price, and so on). This property must be null for complex
     * fields. Fields of type Edm.GeographyPoint or
     * Collection(Edm.GeographyPoint) cannot be facetable. Default is true for
     * all other simple fields.
     *
     * @return the facetable value.
     */
    public [CtTypeReferenceImpl]java.lang.Boolean isFacetable() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.facetable;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the facetable property: A value indicating whether to enable the
     * field to be referenced in facet queries. Typically used in a
     * presentation of search results that includes hit count by category (for
     * example, search for digital cameras and see hits by brand, by
     * megapixels, by price, and so on). This property must be null for complex
     * fields. Fields of type Edm.GeographyPoint or
     * Collection(Edm.GeographyPoint) cannot be facetable. Default is true for
     * all other simple fields.
     *
     * @param facetable
     * 		the facetable value to set.
     * @return the Field object itself.
     */
    public [CtTypeReferenceImpl]com.azure.search.documents.models.Field setFacetable([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean facetable) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.facetable = [CtVariableReadImpl]facetable;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the analyzer property: The name of the analyzer to use for the
     * field. This option can be used only with searchable fields and it can't
     * be set together with either searchAnalyzer or indexAnalyzer. Once the
     * analyzer is chosen, it cannot be changed for the field. Must be null for
     * complex fields. Possible values include: 'ArMicrosoft', 'ArLucene',
     * 'HyLucene', 'BnMicrosoft', 'EuLucene', 'BgMicrosoft', 'BgLucene',
     * 'CaMicrosoft', 'CaLucene', 'ZhHansMicrosoft', 'ZhHansLucene',
     * 'ZhHantMicrosoft', 'ZhHantLucene', 'HrMicrosoft', 'CsMicrosoft',
     * 'CsLucene', 'DaMicrosoft', 'DaLucene', 'NlMicrosoft', 'NlLucene',
     * 'EnMicrosoft', 'EnLucene', 'EtMicrosoft', 'FiMicrosoft', 'FiLucene',
     * 'FrMicrosoft', 'FrLucene', 'GlLucene', 'DeMicrosoft', 'DeLucene',
     * 'ElMicrosoft', 'ElLucene', 'GuMicrosoft', 'HeMicrosoft', 'HiMicrosoft',
     * 'HiLucene', 'HuMicrosoft', 'HuLucene', 'IsMicrosoft', 'IdMicrosoft',
     * 'IdLucene', 'GaLucene', 'ItMicrosoft', 'ItLucene', 'JaMicrosoft',
     * 'JaLucene', 'KnMicrosoft', 'KoMicrosoft', 'KoLucene', 'LvMicrosoft',
     * 'LvLucene', 'LtMicrosoft', 'MlMicrosoft', 'MsMicrosoft', 'MrMicrosoft',
     * 'NbMicrosoft', 'NoLucene', 'FaLucene', 'PlMicrosoft', 'PlLucene',
     * 'PtBrMicrosoft', 'PtBrLucene', 'PtPtMicrosoft', 'PtPtLucene',
     * 'PaMicrosoft', 'RoMicrosoft', 'RoLucene', 'RuMicrosoft', 'RuLucene',
     * 'SrCyrillicMicrosoft', 'SrLatinMicrosoft', 'SkMicrosoft', 'SlMicrosoft',
     * 'EsMicrosoft', 'EsLucene', 'SvMicrosoft', 'SvLucene', 'TaMicrosoft',
     * 'TeMicrosoft', 'ThMicrosoft', 'ThLucene', 'TrMicrosoft', 'TrLucene',
     * 'UkMicrosoft', 'UrMicrosoft', 'ViMicrosoft', 'StandardLucene',
     * 'StandardAsciiFoldingLucene', 'Keyword', 'Pattern', 'Simple', 'Stop',
     * 'Whitespace'.
     *
     * @return the analyzer value.
     */
    public [CtTypeReferenceImpl]com.azure.search.documents.models.AnalyzerName getAnalyzer() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.analyzer;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the analyzer property: The name of the analyzer to use for the
     * field. This option can be used only with searchable fields and it can't
     * be set together with either searchAnalyzer or indexAnalyzer. Once the
     * analyzer is chosen, it cannot be changed for the field. Must be null for
     * complex fields. Possible values include: 'ArMicrosoft', 'ArLucene',
     * 'HyLucene', 'BnMicrosoft', 'EuLucene', 'BgMicrosoft', 'BgLucene',
     * 'CaMicrosoft', 'CaLucene', 'ZhHansMicrosoft', 'ZhHansLucene',
     * 'ZhHantMicrosoft', 'ZhHantLucene', 'HrMicrosoft', 'CsMicrosoft',
     * 'CsLucene', 'DaMicrosoft', 'DaLucene', 'NlMicrosoft', 'NlLucene',
     * 'EnMicrosoft', 'EnLucene', 'EtMicrosoft', 'FiMicrosoft', 'FiLucene',
     * 'FrMicrosoft', 'FrLucene', 'GlLucene', 'DeMicrosoft', 'DeLucene',
     * 'ElMicrosoft', 'ElLucene', 'GuMicrosoft', 'HeMicrosoft', 'HiMicrosoft',
     * 'HiLucene', 'HuMicrosoft', 'HuLucene', 'IsMicrosoft', 'IdMicrosoft',
     * 'IdLucene', 'GaLucene', 'ItMicrosoft', 'ItLucene', 'JaMicrosoft',
     * 'JaLucene', 'KnMicrosoft', 'KoMicrosoft', 'KoLucene', 'LvMicrosoft',
     * 'LvLucene', 'LtMicrosoft', 'MlMicrosoft', 'MsMicrosoft', 'MrMicrosoft',
     * 'NbMicrosoft', 'NoLucene', 'FaLucene', 'PlMicrosoft', 'PlLucene',
     * 'PtBrMicrosoft', 'PtBrLucene', 'PtPtMicrosoft', 'PtPtLucene',
     * 'PaMicrosoft', 'RoMicrosoft', 'RoLucene', 'RuMicrosoft', 'RuLucene',
     * 'SrCyrillicMicrosoft', 'SrLatinMicrosoft', 'SkMicrosoft', 'SlMicrosoft',
     * 'EsMicrosoft', 'EsLucene', 'SvMicrosoft', 'SvLucene', 'TaMicrosoft',
     * 'TeMicrosoft', 'ThMicrosoft', 'ThLucene', 'TrMicrosoft', 'TrLucene',
     * 'UkMicrosoft', 'UrMicrosoft', 'ViMicrosoft', 'StandardLucene',
     * 'StandardAsciiFoldingLucene', 'Keyword', 'Pattern', 'Simple', 'Stop',
     * 'Whitespace'.
     *
     * @param analyzer
     * 		the analyzer value to set.
     * @return the Field object itself.
     */
    public [CtTypeReferenceImpl]com.azure.search.documents.models.Field setAnalyzer([CtParameterImpl][CtTypeReferenceImpl]com.azure.search.documents.models.AnalyzerName analyzer) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.analyzer = [CtVariableReadImpl]analyzer;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the searchAnalyzer property: The name of the analyzer used at search
     * time for the field. This option can be used only with searchable fields.
     * It must be set together with indexAnalyzer and it cannot be set together
     * with the analyzer option. This property cannot be set to the name of a
     * language analyzer; use the analyzer property instead if you need a
     * language analyzer. This analyzer can be updated on an existing field.
     * Must be null for complex fields. Possible values include: 'ArMicrosoft',
     * 'ArLucene', 'HyLucene', 'BnMicrosoft', 'EuLucene', 'BgMicrosoft',
     * 'BgLucene', 'CaMicrosoft', 'CaLucene', 'ZhHansMicrosoft',
     * 'ZhHansLucene', 'ZhHantMicrosoft', 'ZhHantLucene', 'HrMicrosoft',
     * 'CsMicrosoft', 'CsLucene', 'DaMicrosoft', 'DaLucene', 'NlMicrosoft',
     * 'NlLucene', 'EnMicrosoft', 'EnLucene', 'EtMicrosoft', 'FiMicrosoft',
     * 'FiLucene', 'FrMicrosoft', 'FrLucene', 'GlLucene', 'DeMicrosoft',
     * 'DeLucene', 'ElMicrosoft', 'ElLucene', 'GuMicrosoft', 'HeMicrosoft',
     * 'HiMicrosoft', 'HiLucene', 'HuMicrosoft', 'HuLucene', 'IsMicrosoft',
     * 'IdMicrosoft', 'IdLucene', 'GaLucene', 'ItMicrosoft', 'ItLucene',
     * 'JaMicrosoft', 'JaLucene', 'KnMicrosoft', 'KoMicrosoft', 'KoLucene',
     * 'LvMicrosoft', 'LvLucene', 'LtMicrosoft', 'MlMicrosoft', 'MsMicrosoft',
     * 'MrMicrosoft', 'NbMicrosoft', 'NoLucene', 'FaLucene', 'PlMicrosoft',
     * 'PlLucene', 'PtBrMicrosoft', 'PtBrLucene', 'PtPtMicrosoft',
     * 'PtPtLucene', 'PaMicrosoft', 'RoMicrosoft', 'RoLucene', 'RuMicrosoft',
     * 'RuLucene', 'SrCyrillicMicrosoft', 'SrLatinMicrosoft', 'SkMicrosoft',
     * 'SlMicrosoft', 'EsMicrosoft', 'EsLucene', 'SvMicrosoft', 'SvLucene',
     * 'TaMicrosoft', 'TeMicrosoft', 'ThMicrosoft', 'ThLucene', 'TrMicrosoft',
     * 'TrLucene', 'UkMicrosoft', 'UrMicrosoft', 'ViMicrosoft',
     * 'StandardLucene', 'StandardAsciiFoldingLucene', 'Keyword', 'Pattern',
     * 'Simple', 'Stop', 'Whitespace'.
     *
     * @return the searchAnalyzer value.
     */
    public [CtTypeReferenceImpl]com.azure.search.documents.models.AnalyzerName getSearchAnalyzer() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.searchAnalyzer;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the searchAnalyzer property: The name of the analyzer used at search
     * time for the field. This option can be used only with searchable fields.
     * It must be set together with indexAnalyzer and it cannot be set together
     * with the analyzer option. This property cannot be set to the name of a
     * language analyzer; use the analyzer property instead if you need a
     * language analyzer. This analyzer can be updated on an existing field.
     * Must be null for complex fields. Possible values include: 'ArMicrosoft',
     * 'ArLucene', 'HyLucene', 'BnMicrosoft', 'EuLucene', 'BgMicrosoft',
     * 'BgLucene', 'CaMicrosoft', 'CaLucene', 'ZhHansMicrosoft',
     * 'ZhHansLucene', 'ZhHantMicrosoft', 'ZhHantLucene', 'HrMicrosoft',
     * 'CsMicrosoft', 'CsLucene', 'DaMicrosoft', 'DaLucene', 'NlMicrosoft',
     * 'NlLucene', 'EnMicrosoft', 'EnLucene', 'EtMicrosoft', 'FiMicrosoft',
     * 'FiLucene', 'FrMicrosoft', 'FrLucene', 'GlLucene', 'DeMicrosoft',
     * 'DeLucene', 'ElMicrosoft', 'ElLucene', 'GuMicrosoft', 'HeMicrosoft',
     * 'HiMicrosoft', 'HiLucene', 'HuMicrosoft', 'HuLucene', 'IsMicrosoft',
     * 'IdMicrosoft', 'IdLucene', 'GaLucene', 'ItMicrosoft', 'ItLucene',
     * 'JaMicrosoft', 'JaLucene', 'KnMicrosoft', 'KoMicrosoft', 'KoLucene',
     * 'LvMicrosoft', 'LvLucene', 'LtMicrosoft', 'MlMicrosoft', 'MsMicrosoft',
     * 'MrMicrosoft', 'NbMicrosoft', 'NoLucene', 'FaLucene', 'PlMicrosoft',
     * 'PlLucene', 'PtBrMicrosoft', 'PtBrLucene', 'PtPtMicrosoft',
     * 'PtPtLucene', 'PaMicrosoft', 'RoMicrosoft', 'RoLucene', 'RuMicrosoft',
     * 'RuLucene', 'SrCyrillicMicrosoft', 'SrLatinMicrosoft', 'SkMicrosoft',
     * 'SlMicrosoft', 'EsMicrosoft', 'EsLucene', 'SvMicrosoft', 'SvLucene',
     * 'TaMicrosoft', 'TeMicrosoft', 'ThMicrosoft', 'ThLucene', 'TrMicrosoft',
     * 'TrLucene', 'UkMicrosoft', 'UrMicrosoft', 'ViMicrosoft',
     * 'StandardLucene', 'StandardAsciiFoldingLucene', 'Keyword', 'Pattern',
     * 'Simple', 'Stop', 'Whitespace'.
     *
     * @param searchAnalyzer
     * 		the searchAnalyzer value to set.
     * @return the Field object itself.
     */
    public [CtTypeReferenceImpl]com.azure.search.documents.models.Field setSearchAnalyzer([CtParameterImpl][CtTypeReferenceImpl]com.azure.search.documents.models.AnalyzerName searchAnalyzer) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.searchAnalyzer = [CtVariableReadImpl]searchAnalyzer;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the indexAnalyzer property: The name of the analyzer used at
     * indexing time for the field. This option can be used only with
     * searchable fields. It must be set together with searchAnalyzer and it
     * cannot be set together with the analyzer option.  This property cannot
     * be set to the name of a language analyzer; use the analyzer property
     * instead if you need a language analyzer. Once the analyzer is chosen, it
     * cannot be changed for the field. Must be null for complex fields.
     * Possible values include: 'ArMicrosoft', 'ArLucene', 'HyLucene',
     * 'BnMicrosoft', 'EuLucene', 'BgMicrosoft', 'BgLucene', 'CaMicrosoft',
     * 'CaLucene', 'ZhHansMicrosoft', 'ZhHansLucene', 'ZhHantMicrosoft',
     * 'ZhHantLucene', 'HrMicrosoft', 'CsMicrosoft', 'CsLucene', 'DaMicrosoft',
     * 'DaLucene', 'NlMicrosoft', 'NlLucene', 'EnMicrosoft', 'EnLucene',
     * 'EtMicrosoft', 'FiMicrosoft', 'FiLucene', 'FrMicrosoft', 'FrLucene',
     * 'GlLucene', 'DeMicrosoft', 'DeLucene', 'ElMicrosoft', 'ElLucene',
     * 'GuMicrosoft', 'HeMicrosoft', 'HiMicrosoft', 'HiLucene', 'HuMicrosoft',
     * 'HuLucene', 'IsMicrosoft', 'IdMicrosoft', 'IdLucene', 'GaLucene',
     * 'ItMicrosoft', 'ItLucene', 'JaMicrosoft', 'JaLucene', 'KnMicrosoft',
     * 'KoMicrosoft', 'KoLucene', 'LvMicrosoft', 'LvLucene', 'LtMicrosoft',
     * 'MlMicrosoft', 'MsMicrosoft', 'MrMicrosoft', 'NbMicrosoft', 'NoLucene',
     * 'FaLucene', 'PlMicrosoft', 'PlLucene', 'PtBrMicrosoft', 'PtBrLucene',
     * 'PtPtMicrosoft', 'PtPtLucene', 'PaMicrosoft', 'RoMicrosoft', 'RoLucene',
     * 'RuMicrosoft', 'RuLucene', 'SrCyrillicMicrosoft', 'SrLatinMicrosoft',
     * 'SkMicrosoft', 'SlMicrosoft', 'EsMicrosoft', 'EsLucene', 'SvMicrosoft',
     * 'SvLucene', 'TaMicrosoft', 'TeMicrosoft', 'ThMicrosoft', 'ThLucene',
     * 'TrMicrosoft', 'TrLucene', 'UkMicrosoft', 'UrMicrosoft', 'ViMicrosoft',
     * 'StandardLucene', 'StandardAsciiFoldingLucene', 'Keyword', 'Pattern',
     * 'Simple', 'Stop', 'Whitespace'.
     *
     * @return the indexAnalyzer value.
     */
    public [CtTypeReferenceImpl]com.azure.search.documents.models.AnalyzerName getIndexAnalyzer() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.indexAnalyzer;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the indexAnalyzer property: The name of the analyzer used at
     * indexing time for the field. This option can be used only with
     * searchable fields. It must be set together with searchAnalyzer and it
     * cannot be set together with the analyzer option.  This property cannot
     * be set to the name of a language analyzer; use the analyzer property
     * instead if you need a language analyzer. Once the analyzer is chosen, it
     * cannot be changed for the field. Must be null for complex fields.
     * Possible values include: 'ArMicrosoft', 'ArLucene', 'HyLucene',
     * 'BnMicrosoft', 'EuLucene', 'BgMicrosoft', 'BgLucene', 'CaMicrosoft',
     * 'CaLucene', 'ZhHansMicrosoft', 'ZhHansLucene', 'ZhHantMicrosoft',
     * 'ZhHantLucene', 'HrMicrosoft', 'CsMicrosoft', 'CsLucene', 'DaMicrosoft',
     * 'DaLucene', 'NlMicrosoft', 'NlLucene', 'EnMicrosoft', 'EnLucene',
     * 'EtMicrosoft', 'FiMicrosoft', 'FiLucene', 'FrMicrosoft', 'FrLucene',
     * 'GlLucene', 'DeMicrosoft', 'DeLucene', 'ElMicrosoft', 'ElLucene',
     * 'GuMicrosoft', 'HeMicrosoft', 'HiMicrosoft', 'HiLucene', 'HuMicrosoft',
     * 'HuLucene', 'IsMicrosoft', 'IdMicrosoft', 'IdLucene', 'GaLucene',
     * 'ItMicrosoft', 'ItLucene', 'JaMicrosoft', 'JaLucene', 'KnMicrosoft',
     * 'KoMicrosoft', 'KoLucene', 'LvMicrosoft', 'LvLucene', 'LtMicrosoft',
     * 'MlMicrosoft', 'MsMicrosoft', 'MrMicrosoft', 'NbMicrosoft', 'NoLucene',
     * 'FaLucene', 'PlMicrosoft', 'PlLucene', 'PtBrMicrosoft', 'PtBrLucene',
     * 'PtPtMicrosoft', 'PtPtLucene', 'PaMicrosoft', 'RoMicrosoft', 'RoLucene',
     * 'RuMicrosoft', 'RuLucene', 'SrCyrillicMicrosoft', 'SrLatinMicrosoft',
     * 'SkMicrosoft', 'SlMicrosoft', 'EsMicrosoft', 'EsLucene', 'SvMicrosoft',
     * 'SvLucene', 'TaMicrosoft', 'TeMicrosoft', 'ThMicrosoft', 'ThLucene',
     * 'TrMicrosoft', 'TrLucene', 'UkMicrosoft', 'UrMicrosoft', 'ViMicrosoft',
     * 'StandardLucene', 'StandardAsciiFoldingLucene', 'Keyword', 'Pattern',
     * 'Simple', 'Stop', 'Whitespace'.
     *
     * @param indexAnalyzer
     * 		the indexAnalyzer value to set.
     * @return the Field object itself.
     */
    public [CtTypeReferenceImpl]com.azure.search.documents.models.Field setIndexAnalyzer([CtParameterImpl][CtTypeReferenceImpl]com.azure.search.documents.models.AnalyzerName indexAnalyzer) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.indexAnalyzer = [CtVariableReadImpl]indexAnalyzer;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the synonymMaps property: A list of the names of synonym maps to
     * associate with this field. This option can be used only with searchable
     * fields. Currently only one synonym map per field is supported. Assigning
     * a synonym map to a field ensures that query terms targeting that field
     * are expanded at query-time using the rules in the synonym map. This
     * attribute can be changed on existing fields. Must be null or an empty
     * collection for complex fields.
     *
     * @return the synonymMaps value.
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getSynonymMaps() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.synonymMaps;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the synonymMaps property: A list of the names of synonym maps to
     * associate with this field. This option can be used only with searchable
     * fields. Currently only one synonym map per field is supported. Assigning
     * a synonym map to a field ensures that query terms targeting that field
     * are expanded at query-time using the rules in the synonym map. This
     * attribute can be changed on existing fields. Must be null or an empty
     * collection for complex fields.
     *
     * @param synonymMaps
     * 		the synonymMaps value to set.
     * @return the Field object itself.
     */
    public [CtTypeReferenceImpl]com.azure.search.documents.models.Field setSynonymMaps([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> synonymMaps) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.synonymMaps = [CtVariableReadImpl]synonymMaps;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the fields property: A list of sub-fields if this is a field of type
     * Edm.ComplexType or Collection(Edm.ComplexType). Must be null or empty
     * for simple fields.
     *
     * @return the fields value.
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.azure.search.documents.models.Field> getFields() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl][CtThisAccessImpl]this.fields;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the fields property: A list of sub-fields if this is a field of type
     * Edm.ComplexType or Collection(Edm.ComplexType). Must be null or empty
     * for simple fields.
     *
     * @param fields
     * 		the fields value to set.
     * @return the Field object itself.
     */
    public [CtTypeReferenceImpl]com.azure.search.documents.models.Field setFields([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.azure.search.documents.models.Field> fields) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.fields = [CtVariableReadImpl]fields;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Get the hidden property: A value indicating whether the field will be
     * returned in a search result. This property must be false for key fields,
     * and must be null for complex fields. You can hide a field from search
     * results if you want to use it only as a filter, for sorting, or for
     * scoring. This property can also be changed on existing fields and
     * enabling it does not cause an increase in index storage requirements.
     *
     * @return the hidden value.
     */
    public [CtTypeReferenceImpl]java.lang.Boolean isHidden() [CtBlockImpl]{
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtFieldReadImpl]retrievable == [CtLiteralImpl]null ? [CtLiteralImpl]null : [CtUnaryOperatorImpl]![CtFieldReadImpl]retrievable;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Set the hidden property: A value indicating whether the field will be
     * returned in a search result. This property must be false for key fields,
     * and must be null for complex fields. You can hide a field from search
     * results if you want to use it only as a filter, for sorting, or for
     * scoring. This property can also be changed on existing fields and
     * enabling it does not cause an increase in index storage requirements.
     *
     * @param hidden
     * 		the hidden value to set.
     * @return the Field object itself.
     */
    public [CtTypeReferenceImpl]com.azure.search.documents.models.Field setHidden([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean hidden) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]retrievable = [CtConditionalImpl]([CtBinaryOperatorImpl][CtFieldReadImpl][CtThisAccessImpl]this.hidden == [CtLiteralImpl]null) ? [CtLiteralImpl]null : [CtUnaryOperatorImpl]![CtFieldReadImpl][CtThisAccessImpl]this.hidden;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.hidden = [CtVariableReadImpl]hidden;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }
}