package com.example.kafkademo.exception;

/**
 * @author renjunzhou<br>
 * Description: 4   ErrorCode  <br>
 * Date: Create  2017/7/6<br>
 * Modified By:<br>
 * 1.  2017/09/08   add errorCode
 * 2.Chen Zhenkai 2018/12/04   add errorCode
 */
public enum ErrorCode {
    /**
     *
     */
    BASE_JPA_ID_NULL,
    E2104,
    E3114,
    E3202,
    E3111,
    E3208,
    E4101,
    E4109,
    E4105,
    E4106,
    E4107,
    E4302,
    E4303,
    E4305,

    ASENDIA_BDD_FAIL,
    ASENDIA_DESTOCKING_BDD_NOT_DEFINED,
    ASENDIA_HK_BDD_FAIL,
    ASENDIA_HK_BDD_TRANSFERCENTER_NOT_EXIST,
    ASENDIA_HK_PRE_FAIL,
    ASENDIA_INVOICES_IS_EMPTY,

    BARCODE_INCORRECT,
    BASE_JPA_CRITERIAQUERY_NULL,
    BASE_JPA_DOMAIN_CLASS_NULL,
    BASE_JPA_ENTITY_NULL,
    BASE_JPA_EXAMPLE_NULL,
    BASE_JPA_ITERABLE_ENTITY_NULL,
    BASE_JPA_ITERABLE_IDS_NULL,
    BASE_JPA_TYPEQUERY_NULL,
    BATCH_DUPLICATION_FORBIDDEN,
    BATCH_EXIST,
    BATCH_NOT_FOUND,
    BATCH_NOT_AMEND_COMMANDS,
    BOX_NOT_FOUND,
    BOX_NUMBER_NOT_MATCH,
    BOX_HAVE_NOT_STOCK_IN_AT,
    BILL_INVOICE_DONE,
    BILL_INVOICE_SETTLEMENT,
    BILL_NOTCREATED_OR_AMOUNT_NOTZERO,
    BILL_SETTLED,
    BILL_INVOICE_NOT_FOUND,
    BILL_INVOICE_CAN_NOT_DISCARD,
    BILL_HAVE_MORE_THAN_ONE_BILLINVOICEDID,
    BOX_NUMBER_NOT_EQUAL_TOTAL,
    BOX_SERVICE_ALREADY_SCANNED,
    BOX_SERVICE_NOT_SAME_TRANSFER_CENTER,
    BOX_CONFLICT_PALLET_TRANSFER_CENTER,
    BOX_CONFLICT_PALLET_LOADING_TYPE,
    BOX_SERVICE_ALREADY_STOCK_IN,
    BOX_SERVICE_NO_BOX_ID,
    BOX_SERVICE_ARCHIVE,
    BOX_SERVICE_ARCHIVE_NOT_SAME_COMMAND,
    BOX_SERVICE_NO_CORRESPONDING_DATA,
    BOX_NOT_IMPORT_TYPE,
    BOX_NOT_SAME_COMMAND,
    BOX_NOT_EXPORT_TYPE,
    BOX_ALEARDY_EXIST_PRE_STOCK_OUT_AT,
    BOX_ALEARDY_STOCK_IN,
    BOX_NOT_ATTACH_COMMAND,
    BOX_NOT_EXIST,
    BOX_EXIST,


    CANT_CALL_THIS_METHOD,
    CARRIER_ACCOUNT_NOT_FOUND,
    CARRIER_NOT_FOUND,
    CITY_NAME_WRONG,
    CLIENT_NAME_FORBIDDEND,
    CLIENT_NOT_FOUND,
    CLIENT_NOT_IDENTICAL,
    CLEARANCE_AWB_MISMATCH,
    CLEARANCE_CSV_EMPTY,
    CLEARANCE_DOWNLOAD_FORBIDDEN,
    CLEARANCE_PARCEL_QUANTITY_MISMATCH,
    CLEARANCE_PACKAGE_QUANTITY_MISMATCH,
    CLEARANCE_VALUELEVEL_MISMATCH,
    CLEARANCE_ITEM_VALUE_DUPLICATION_FORBIDDEN,
    CLEARANCE_LOW_VALUE_ITEM_VALUE_MISMATCH,
    CLEARANCE_MISMATCH_HSCODE,
    CLEARANCE_R_COUNTRY,
    CLEARANCE_REPEAT_RECIPIENT,
    CLEARANCE_REPEAT_RECIPIENT_ADDRESS,
    CLEARANCE_S_COUNTRY,
    CLEARANCE_S_ADDRESS1,
    CLEARANCE_SHIPMENT_ATTACH,
    CLEARANCE_SHIPMENT_ATTACH_COMMAND_ZERO,
    CMR_DESTOCKING_RECEIVER_EMPTY,
    CMR_DOWNLOAD_OR_UPLOAD_FORBIDDEN,
    CMR_FILE_NOT_UPLOADED,
    CMR_NOT_UPLOAD,
    COMFIRMED_PAYMENT_CANT_DIMISSED,
    COMMAND_ID_NULL,
    COMMAND_PRICING_UNDEFINED,
    COMMAND_QUANTITY_WRONG_REUPLOAD,
    COMMAND_SUPPLIER_NULL,

    DESTOCKING_ALREADY_ARCHIVE,
    DESTOCKING_ALREADY_STOCK_OUT,
    DESTOCKING_NOT_FOUND,
    DESTOCKING_STATUS_INVALIDATE,
    DESTOCKING_CAN_NOT_DELETE,

    ELEMENT_CONFLICT,
    EMAIL_RECEIVER_IS_EMPTY,
    EXCEL_DATA_IS_EMPTY,
    EXCEL_GENERATE_ERROR,
    ES_EMAIL_EXCEPTION,


    FILE_DOWNLOAD_IS_EMPTY,

    HOUSE_BILL_NOT_FOUND,

    INSUFFICIENT_BALANCE,

    LINE_CANT_RETURN,


    NOT_ATTATCH_COMMAND,
    NOT_COMFIRMED,
    NOT_FOUND,
    NOT_MATCH_ACTUAL_SITUATION,
    NOT_SAME_WAREHOUSE,

    ORDER_NOT_FOUND,
    ORDER_PORT_NOT_VALID,
    WAREHOUSE_NOT_FOUND,

    PACKAGE_NOT_BELONG,
    PALLET_NOT_SUPPORT_INTERNAL_SCAN,
    PALLET_CANNOT_ARCHIVE,
    PALLET_ALREADY_ARCHIVE,
    PALLET_SERVICE_NOT_EXIST,
    PALLET_ALREADY_STOCK_OUT,
    PALLET_ALREADY_CAR,
    PALLET_BOX_ZERO,
    PALLET_NOT_FOUND,
    PALLETS_NOT_EXIST,
    PALLETS_PARAM_NOT_EXIST,
    PALLET_ALEARDY_RELATION_OTHER_DESTOCKING,
    PALLET_ALEARDY_RELATION_THIS_DESTOCKING,
    PALLETS_CONFLIT_DESTOCKING_TRANSFER_CENTER,
    PARAMETER_CHECK_FAIL,
    PDA_BOX_HASSCANNED_ERROR,
    PDA_BOX_NOT_BELONGS_BATCH,
    PDA_BOX_NOT_DELTAX,
    PDA_BOX_NOT_FOUND,
    PDA_BOX_UNKNOWN_ERROR,
    PDA_BOX_SUBMIT_FAIL,
    PDA_PALLET_ID_NULL,
    PDA_PARCEL_HASSCANNED_ERROR,
    PDA_PARCEL_NOT_BELONGS_BATCH,
    PDA_PARCEL_NOT_DELTAX,
    PDA_PARCEL_NOT_FOUND,
    PDA_PARCEL_UNKNOWN_ERROR,
    PDA_PARCEL_SUBMIT_FAIL,
    PDA_TRANSFERCENTER_CONFLICT,
    PRIX_DUPLICATED,

    POD_FILE_NOT_UPLOADED,
    PALLET_TRANSFERCENTER_CONFLICT,
    ROUTE_EXIST,
    ROUTE_NOT_EXIST,
    RSQL_TYPEQUERY_NULL,
    ROUTE_ADDRESS_NOT_EXIST,

    SCHEDULE_NOT_EXIST,
    SUPPLIER_EXIST,
    SEPERATE_DESTOCKING_ASSCCIATION,
    SEPERATE_DESTOCKING_SAME_WAREHOUSE,
    SHIPMENT_COLISSIMO_WEIGHT_NOT_ALLOWED,
    STOCK_OUT_NOTIFY_CLIENT_DATA_EMPTY,

    TRANSFOR_OEDER_NETWEIGHT_ZERO,
    TRANSFERCENTER_NOT_EXIST,
    TRANSFERCENTER_NOT_SAME,


    CAN_NOT_SET_COST,
    VEHICLE_CAN_NOT_SET_COST,


    INBOUND_ORDER_NOT_FOUND,

    AWB_UNVALIDATED,
    ENTER_ID_NOT_CORRECT,
    ENTER_ORDER_ID_NOT_CORRECT,
    ENTER_SAME_SCAN,

    ENTER_ULD_NOT_EXIST,

    EX_STOCK_ULD_ERROR,

    EX_STOCK_UNKNOWN_BOX_OR_ITEM,
    EX_STOCK_ITEM_TYPE_ERROR,
    EX_STOCK_NOT_CLEARANCE,
    BOX_ALREADY_OPERATED,
    ITEMS_NOT_FOUND,
    ITEMS_ALL_RECORDED,

    PARCEL_WEIGHT_GREATER_THAN_ZERO,

    PARCEL_SAMPLED,

    AKE_BAGLABLECODE_REPEATED,
    AKE_SORTCODE_NOT_FOUND,

    DRIVER_NOT_EXIST,

    PICK_UP_ROLLBACK_WAREHOUSE_HAVE_OPERATION,
    PICK_UP_ARCHIVE_NOT_ALLOWED_F,
    PICK_UP_ARCHIVE_WAREHOUSE_HAVE_OPERATION,
    TRANSFER_QUANTITY_OVER_THE_MAX,

    SWAP_BODY_ID_ONLY_BE_NUMBERS,
    SWAP_BODY_NOT_FOUND,
    SWAP_BODY_BOX_ZERO,
    SWAP_BODY_EXIST,
    SWAP_BODY_ALREAY_STOCK_OUT,
    SWAP_BODY_CLOSED,
    DESTOCKING_SWAP_BODY_OUT_OF_RANGE,
    SWAP_BODY_REMAIN_BOX,
    PLOMB_CHECK_ERROR,
    DESTOCKING_SWAP_BODY_NOT_MATCH,
    SWAP_BODY_PLOMB_NOT_MATCH,
    SWAP_BODY_WITHOUT_PLOMB,
    SWAP_BODY_EMPTY_FORBID_CLOSE,
    MAWB_NOT_EXIST_OMS_FORBID_RECORD,
    PARCEL_HAVE_INSPECTED,
    DESTOCKING_DEPART_AT_CAN_NOT_BEFORE_LAST_LOAD_AT,
    STOCK_TAKING_CREATED,
    STOCK_TAKING_TODAY_SCANNED,
    STOCK_TAKING_PALLET_TYPE,
    BOX_TRUCKED_DESTOCKING_LOAD,
    BOX_TRUCKED_DESTOCKING_PALLET,
    BOX_TRUCKED_DESTOCKING_SWAP_BODY,
    BOX_TRUCKED_DESTOCKING_STOCKOUT,
    PALLET_REMOVE_BOX_STATUS_DISALLOWED,
    BOX_HAS_PARCEL_CONTROLLED,
    BOX_NOT_CLEARED_CUSTOMS,
    PALLET_HAS_PARCEL_CONTROLLED,
    PALLET_NOT_CLEARED_CUSTOMS,
    COMMAND_HAS_PARCEL_CONTROLLED,
    COMMAND_NOT_CLEARED_CUSTOMS,
    ACCOUNT_NOT_FOUND,
    SUPPORT_TYPE_ALREADY_EXIST,
    QUESTION_RESPONSE_NO_NULL,
    SUPPORT_TYPE_NOT_DELETE,
    RESPONSE_ALREADY_EXIST,
    LANDINGAIRPORT_NOT_EXIST,
    BATCH_STATUS_NOT_ATTACH,
    SHIPMENT_PRICING_ALREADY_EXIST,
    ARTICLE_CREATED_CONFLICT_EXCEPTION_BY_HSCODE,
    ARTICLE_DELETE_NOT_EXISTS_EXCEPTION_BY_HSCODE,
    ARTICLE_NOT_FOUND,
    ARTICLE_UPDATE_NOT_EXISTS_EXCEPTION_BY_HSCODE,

    BATCH_ALREADY_FINISHED,
    BATCH_CANT_MODIFY,

    BATCH_IS_ASSOCIATE_WITH_COMMAND,

    BATCH_REQUIRED,
    BATCH_FLIGHT_NUMBER_NOT_MATCH,

    CLEARANCE_BOX_NUMBER_NOT_MATCH,
    BATCH_PRESTOCKIN_NOT_EXIST_COMMAND,
    BATCH_STATUS_INVALID,
    BILL_ALREADY_CLEARED,

    ECC_NO_COMMAND,


    CLIENT_BALANCE_NOT_FOUND,

    CLEARANCE_PDF_EMPTY,

    CLEARANCE_UPLOAD_FAIL,
    CLEARING_NOT_FOUND,

    COMMAND_ALREADY_ATTACHED,
    COMMAND_ALREADY_DETACHED,
    COMMAND_CANT_MODIFY,
    COMMAND_DECLARE_WEIGHT_NET_WEIGHT_FORBIDEN,
    COMMAND_DELETE_ERROR_ATTACHED_SHIPMENT,
    COMMAND_NOT_EMPTY,
    COMMAND_NOT_FOUND,
    LD3_NOT_FOUND,
    COMMAND_PACKAGE_REQUIRED,
    COMMAND_PARCELS_NUMBER_INVALID,

    COMMAND_PRICING_UNDEFINED_CLIENT,
    BATCH_PRICING_UNDEFINED,
    SPAIN_BATCH_PRICING_UNDEFINED,
    SPAIN_COMMAND_PRICING_UNDEFINED,

    COMMAND_PACHAGES_QUANTITY_WRONG_REUPLOAD,
    REAL_BOXID_REQUIRED,
    COMMAND_REFERANCE_ALREADY_EXISTED,
    COMMAND_STATUS_CAN_NOT_CHANGE_NOW,
    COMMAND_STATUS_INCONSISTENCY,
    COMMAND_STATUS_INVALID,
    COMMAND_STATUS_INVALID_BATCH,
    COMMENT_ALREADY_READ,
    COMMENT_CAN_NOT_BE_NULL,
    COMMENT_REFERENCEID_NOT_BE_NULL,
    COMMENT_NOT_FOUND,
    CORREOS_EMAIL_IS_EMPTY,
    CSV_API,
    CSV_DATE_PAESE_TYPE_INVALID,
    CUSTOMER_ACTIVATED,
    CUSTOMER_FILL_INCORRECT,
    CUSTOMER_STATUS_UNMATCHED,

    DEEP_CLONE_FAIL,
    DESTOCKING_ALREADY_TRANSFERRED,
    DESTOCKING_ASENDIA_CANT_CREATE,
    DESTOCKING_CARPOOL_MASTER_NOT_FOUND,
    DESTOCKING_STATUS_INVALID,
    DESTOCKING_ATTACH_COMMANDS_NOT_ALLOW,
    DESTOCKING_WAREHOUSE_CAN_NOT_NULL,
    DOWNLOAD_PAYMENT_ATTACHMENT_FAIL,

    EMAIL_RECEIVER_ALREADY_EXISTS,
    ERROR_TYPE_UNKNOWN,
    ETA_EXIST,

    EXCEL_PARCEL_BOXID_EMPTY,

    FIELD_NOT_DEFINED,
    FIELD_NOT_FOUND,

    IMPORT_PARCEL_TRACKING_NUMBNER_REPEAT,

    LINE_NO_STOCKOUT,
    LINE_STOCKOUT_MODE,

    MONTHLY_BILL_ALREAY_CLEARED,

    NEED_TRANSFER_ORDER,
    NET_WEGHT_NOT_MATCH_ACTUAL_SITUATION,
    GROSS_WEGHT_LESS_THAN_BOX_WEIGHT,

    NET_WEGHT_NOT_MATCH_ACTUAL_SITUATION_2,
    NO_MIXED_STOCKOUT,

    NO_CLEARANCE_IMPORT_PARCEL_WEIGHT_NOT_VALID,

    OVERSEAS_SHIPMENT_REQUIRED_ITEMS,
    OVERSEAS_SHIPMENT_ZERO_AMOUNT_ITEMS,


    PALLET_REQUIRE,

    PARCEL_NOT_FOUND,
    PDA_BATCH_NOT_FOUND,

    PDA_PALLET_BOX_NULL,
    PDA_PALLET_TRANSFERCENTER_NULL,
    PICKUP_ALEARDY_EXIST,
    PRICING_ALREADY_EXIST,
    PRICING_CARRIER_ACCOUNT_NOT_EXIST,
    PRICING_TYPE_NOT_EXIST,
    PRICING_UPDATE_CLIENTS_TYPE_IS_EMPTY,
    PRICING_UPDATE_TRANSPORTER_TRANSFERCENTER_IS_NULL,
    PRICING_UPDATE_WEIGHTUNIT_MIN_QUANTITYUNIT_UNIT_IS_NULL,
    PRICING_EFFECTIVE_AND_EXPIRE_TIME_IS_NULL,
    OPERATION_PRICING_ALREADY_EXIST,

    RECIPIENT_NAME_NEED_WHITESPACEM,
    RECIPIENT_ADDRESS_TOO_LONG,
    RECIPIENT_NAME_TOO_LONG,
    RECONCILIATIONTASK_AUTOTASK_NEXT_NOT_ALLOWED,
    RECONCILIATIONTASK_BILLING_AFTER_ALL_TASK,
    RECONCILIATIONTASK_EXIST_FILED,
    RECONCILIATIONTASK_STATUS_different,
    RECONCILIATIONTASK_STATUS_NEXT_NOT_ALLOWED,
    RECONCILIATIONTASK_STATUS_NOT_FOUND,
    RECORD_NULL,
    RETURN_SHIPMENT_ALREADY_EXIST,
    RETURN_SHIPMENT_BIN_IN_EMAIL_NOTIFICATION_NOT_FOUND,
    RETURN_SHIPMENT_NOT_FOUND,
    RETURN_SHIPMENT_STATUS_FORBIDDEN,
    RETURN_SHIPMENT_TRANSPORTER_NOT_FOUND,
    RETURNSHIPMENT_CLIENT_ERROR,
    ROLE_ALREADY_EXIST,
    ROLE_NOT_ALLOWED,
    ROUTE_NAME_NOT_FOUND,
    ROUTE_CONFIG_ALREADY_EXIST,
    ROUTE_SHIPMENT_FORBIDDEN,

    SHIPPING_PROBLEM_NOT_FOUND,
    SENDER_NAME_NEED_WHITESPACEM,

    SHIPMENT_ALREADY_ATTACHED,
    SHIPMENT_ALREADY_TRASH,
    SHIPMENT_BATCHCREATE_UPS_FORBIDDEN,
    SHIPMENT_CITY_INVALID,
    SHIPMENT_COLISSIMO_PRICING_WEIGHT_RANGE_UNDEFINED,
    SHIPMENT_CORREOS_UNTRACKED_LABEL_PROVINCE_INVALID,
    SHIPMENT_CORREOS_UNTRACKED_LABEL_ZIPCODE_INVALID,
    SHIPMENT_COUNTRY_INVALID,
    SHIPMENT_LABEL_GENERATE_FAILED,
    SHIPMENT_LABEL_NOT_FOUND,
    SHIPMENT_LIST_NOT_FOUND,
    SHIPMENT_NOT_FOUND,
    SHIPMENT_PRICING_INVALID,
    SHIPMENT_PRICING_IS_EMPTY,
    SHIPMENT_PRICING_MODE_INVALID,
    SHIPMENT_PRICING_MODE_NOT_ALLOWED,
    SHIPMENT_PRICING_RANGE_INVALID,
    SHIPMENT_PRICING_REDELIVERYFEE_UNDEFINED,
    PARTICULAR_SHIPMENT_PRICING_REDELIVERYFEE_UNDEFINED,
    SHIPMENT_PRICING_UNDEFINED,
    SHIPMENT_PRICING_WEIGHT_RANGE_UNDEFINED,
    SHIPMENT_RECIPIENT_STREET_INVALID,
    SHIPMENT_RESOURCE_NOT_FOUND,
    SHIPMENT_ZIPCODE_INVALID,
    SHIPMENT_AT_LEAST_PHONENUMBER_OR_MOBILENUMBER,
    SHIPMENTS_REQUIRED,
    STOCK_OUT_COMMAND_HAS_BE_SAME_WAREHOUSE,
    SUPPORT_ALREADY_EXIST,
    SUPPORT_DECLARATION_IS_EMPTY,
    UNDECLARED_SUPPORT_FOUND,
    CARRIERACCOUNT_NOT_FOUND,
    SHIPMENT_OR_PARCEL_NOT_FOUND,
    SUPPORT_DESCRIPTION_APPLY_RECEIPT_FORBIDDEN,
    SUPPORT_NOT_FOUND,
    SHIPMENT_FILE_REQUIRED,

    TEST_SYSTEM_RELEASE_FAULT,
    TRACKING_NOT_FOUND,
    TRANSFER_ORDER_NO_LINE,
    TRANSFERCENTER_NOT_MATCH,
    TRANSPORTER_CREATE_LABEL_DISALLOWED,
    TRANSPORTER_MANIFEST_STOCK_IN_FORBIDDEN,
    TRANSPORTER_NOT_CONFIG,
    TRANSPORTER_NOT_FOUND,
    REFERENCE_ALREADY_EXIST,
    TRANSPORTER_PROVIDER_CARPOOLFEE,

    TRANSPORTER_RULE_CONFLICT,
    TRANSPORTER_RULE_NOT_FOUND,

    TMS_INTERFACE_GET_COMMANDS_BY_MAWBS_ERROR,

    UNMATCHED_CHARGE_TYPE_OR_PRICE,
    UPLOAD_PAYMENT_ATTACHMENT_FAIL,
    PAYMENT_NOT_FOUND,
    PAYMENT_NOT_ATTACHED,
    PAYMENT_INVOICE_NOT_FOUND,
    UPLOAD_RECONCILIATIONTASK_FAIL,
    UPLOAD_NO_CLEARANCE_FILE_FAIL,

    VALIDATEDAT_EXIST,
    VIAPOST_ZIPCODE_UNSATISFIED,

    WEIGHT_LESS_TOTAL,
    ETA_NOT_EMPTY,
    ATTACHEMENT_NOT_FOUND,
    ROUTE_CREATE_SHIPMENT_MUST_FALSE,
    PERMISSION_FORBIDDEN,
    ID_NOT_EXIST,
    FILE_NAME_ALREADY_EXIT,
    NO_DATA_FOUND_IN_FILE,
    NOT_FOUND_IN_SHIPMENT_MIDDLE,
    SHIPMENT_ALREADY_PROCESSED,
    DISCOUNT_NOT_FOUND,
    I18N_KEY_ALREADY_EXIT,
    I18N_CAN_NOT_BE_NULL,
    QUESTION_TYPE_CAN_NOT_BE_FOUND,
    COMPENSATE_SUPPORT_CAN_NOT_REDECLARE,
    COMPENSATE_SUPPORT_CAN_NOT_CNACEL_DECLARE,
    COMPENSATED_SUPPORT_CAN_NOT_EDIT_OR_DELETE,
    DECLARE_REASON_CAN_NOT_BE_NULL,
    DECLARE_COMMENT_CAN_NOT_BE_NULL,
    DECLARE_TIME_CAN_NOT_BE_NULL,
    DECLARE_TIMES_OUT_OF_LIMIT,


    UPLOAD_ATTACHMENT_TYPE_NOT_FOUND,
    UPLOAD_ATTACHMENT_FAILED,
    DOWNLOAD_ATTACHMENT_FAIL,
    ATTACHMENT_KEY_ERROR,
    UPLOAD_FILE_FAILED,
    RETURN_SHIPMENT_NOT_EXIST,
    BATCH_STATUS_NOT_IS_CREATE,
    DOWNLOAD_RCM_PDF_FAIL,
    FILE_TYPE_NOT_SUPPORTED,
    UPLOAD_MAWB_FAIL,
    DOWNLOAD_MAWB_FAIL,
    PATCH_FAILED,
    SEND_EMAIL_MESSAGE_FAILED,
    S3_SERVICE_COPY_FILE_FAILED,
    S3_CLIENT_COPY_FILE_FAILED,
    S3_DOWNLOAD_OBJECT_IS_NULL,
    LOGICAL_FILTER_IO_EXCEPTION,
    CLIENT_PERMISSION_DENIED,
    AGENT_NOT_FOUND,
    AGENT_PERMISSION_DENIED,
    HTTP_CLIENT_ERROR,
    REDIS_CONFIG_URL_ERROR,
    NOT_LOGIN_YET,
    LOGO_LOAD_FAILED,
    INVOICE_TIME_AND_MONTH_IS_NULL,
    SOME_INVOICE_NOT_IN_THIS_MONTH,
    BILL_NOT_EXIST,
    SPLIT_LAGER_SALE_DETAILS,
    PRICING_NOT_SUPPORT,
    ASENDIA_INVOICE_MULTIPLE_RECORDS,
    RECONCILIATION_TASK_UNDONE,
    WAREHOUSE_TASK_NOT_EXIST,
    INVOICE_NOT_EXIST,
    INVOICE_UPLOAD_FAILED,
    PERMISSION_DENIED,
    CLIENT_BILL_IS_NONE,
    INVOICE_DETAILS_NOT_MATCH_TOTAL,
    AVOIR_NEED_ORIGINAL_INVOICE_ID,
    AVOIR_CANNOT_BE_UPDATE,
    PAYMENT_NOT_EXIST,
    ECC_NOT_EXIST,
    S3_UPLOAD_ERROR,
    S3_DOWNLOAD_ERROR,
    PARCEL_WEIGHT_GREATER_0,
    BOX_NUMBER_MORE_THAN_3_DIGITS,
    COMMAND_STATUS_NOT_CREATE,
    COMMAND_IS_NO_CLEARANCE,
    DECLARE_BOX_MUST_MORE_THAN_UPLOAD_BOX,
    DOWNLOAD_NO_CLEARANCE_FILE_FAIL,
    COMMAND_WEIGHT_NOT_MATCH,
    JSON_FORMAT_ERROR,
    CMS_ERROR,
    XPUSH_ERROR,
    ROUTE_NOT_FOUND,
    SHELL_UTILS_ERROR,
    CSV_SERVICE_IO_EXCEPTION,
    EMAIL_SERVICE_ERROR,
    CAN_NOT_GENERATE_TRACKINGNUMBER,
    ROUTE_CONFIG_NOT_FOUND,
    DATA_BASE_ERROR,
    REDIS_CONNECTION_ERROR,

    PUBLISH_TIME_CAN_NOT_BEFORE_NOW,
    MESSAGE_NOT_FOUND,
    MESSAGE_ALREADY_PUBLISHED,
    DOWNLOAD_ATTACHMENT_NOT_ALLOW,
    RECEIVER_CAN_NOT_BE_NULL,
    PARCEL_LIST_CAN_NOT_BE_NULL,
    UPLOAD_PARCEL_LIST_UNSUPPORTED,
    PARCEL_OR_SHIPMENT_NOT_FOUND,
    SUPPORT_ALREADY_EXIST_AND_IN_COMPANSATE,
    COMPENSATED_SUPPORT_CAN_NOT_DELETE,
    NO_OPERATION_PERMISSION,
    PARCEL_TRANSPORTER_UNSUPPORTED,
    SHIPMENT_TRANSPORTER_UNSUPPORTED,
    SUPPORT_TRANSPORTER_UNSUPPORTED,
    RETURN_SHIPMENT_NOT_APPLY,
    PARAMETER_ID_CHECK_FAIL,
    COMMERCIAL_PARCEL_LIST_CAN_NOT_BE_NULL,
    COMMERCIAL_PARCEL_CAN_NOT_ON_SHELF,
    COMMERCIAL_PARCEL_CAN_NOT_OFF_SHELF,
    COMMERCIAL_PARCEL_PARAMETER_CHECK_FAIL,
    COMMERCIAL_PARCEL_CAN_NOT_DELIVER,
    COMMERCIAL_PARCEL_NOT_PAID_YET,
    COMMERCIAL_PARCEL_CAN_NOT_DELETE,
    COMMERCIAL_PARCEL_CAN_NOT_UPDATE,
    PARCEL_ALREADY_EXIST,
    WARE_HOUSE_AREA_EXIST,
    WARE_HOUSE_FLOOR_EXIST,
    WARE_HOUSE_SHELF_EXIST,
    WARE_HOUSE_SITES_EXIST,
    PARENT_ID_CAN_NOT_BE_NULL,
    PARENT_ID_NOT_EXIST,
    WARE_HOUSE_NOT_EXIST,
    WARE_HOUSE_CAN_NOT_SAME,
    WARE_HOUSE_NOT_EXIST_NUMBER,
    WARE_HOUSE_AREA_NOT_EXIST,
    WARE_HOUSE_LEVEL_ERROR,
    WARE_HOUSE_NOT_EMPTY,
    WARE_HOUSE_NOT_OPERATION,
    WARE_HOUSE_NAME_MUST_BE_UNIQUE,
    WARE_HOUSE_NAME_ILLEGAL,
    WARE_HOUSE_QUANTITY_GREATER_THAN_ZERO,
    WARE_HOUSE_SHELF_OUT_OF_LIMIT,
    WARE_HOUSE_FLOOR_OR_SITE_OUT_OF_LIMIT,
    WARE_HOUSE_SHELF_NAME_ILLEGAL,
    RETURN_SHIPMENT_CAN_NOT_MODIFY_STORAGE_AT_CURRENT_STATUS,
    WARE_HOUSE_LEVEL_FORBIDDEN,
    WARE_HOUSE_TYPE_ERROR,
    BATCH_CONFIRM_INTERRUPTED_BY_CCMS,
    BATCH_ON_SHELF_INTERRUPTED_BY_CCMS,
    BATCH_ROLLBACK_INTERRUPTED_BY_CCMS,
    PRICING_ERROR,
    PRICING_LANDING_AIRPORT_CAN_NOT_BE_NULL,
    NO_CLEARANCE_OPERATION_PRICING_ALREADY_EXIST,
    RETURN_SHIPMENT_CAN_NOT_BINOUT,
    BATCH_CLEARANCE_TYPE_FORBIDDEN,
    BATCH_STATUS_FORBIDDEN,
    CLEARANCE_TYPE_DELTA_X_FORBIDDEN,
    BATCH_ALREADY_STOCK_OUT,
    FBA_CAN_NOT_FALLBACK_STATUS,
    COUNTRY_NOT_FOUND,
    ROUTE_NOT_SUUPORT_MULTIPLE_PARCELS,
    PARCEL_ALREADY_EXISTS,
    COMMAND_STATUS_FORBIDDEN,
    ECC_STATUS_INVALID,
    STATUS_FORBIDDEN,
    B2B_CAN_NOT_FALLBACK_STATUS,
    BOX_ALREADY_ATTACHED,
    COMMAND_BIND_ECC_ALREADY,
    SPAIN_PRICING_NOT_FOUND,
    FBA_PRICING_DEFINED,
    BATCH_EXTEND_NOT_FOUND,
    BATCH_ID_MODIFY_FORBIDDEN,
    EMAIL_NOTIFICATION_FORBIDDEN_MULTI,
    LANDING_AIR_PORT_NOT_MATCH,
    BATCH_ASYNC_FAIL,
    CHARTER_FLIGHTS_NOT_FOUNT,
    BATCH_CHARTER_FLIGHT_ATTACHED_OTHERS,
    CHARTER_FLIGHT_NUMBER_IN_USER,
    SHIPMENT_RECIPIENT_STREET_MUST_NOT_BE_NULL,
    AIRPORT_FLIGHT_RULE_ALREADY_EXIST,
    AIRPORT_FLIGHT_RULE_CHECK_FAIL,
    EMAIL_CONF_NOT_FOUND,
    BATCH_ARGUMENT_ERROR,
    PLEASE_OPERATION_LATER,
    SORTED_BATCH_UNSUPPORTED,
    LD3_NET_WEIGHT_NOT_MATCH,
    SECRET_EXIST,
    SECRET_GENERATE_ERROR,
    SECRET_CLIENT_NULL,
    SECRET_NOT_EXIST,
    PAGE_TOTAL_BIGGEST,
    FLIGHT_ALREADY_EXISTS,
    EMPTY_FIELD,
    REG_FOR_NATIONALITY,
    AIRPORT_REG,
    BATCH_SUPPORT_EXESIT,
    SUPPORT_BATCH_NOT_FOUND,
    HSCODE_ALREADY_EXIST,
    BATCH_SUPPORT_NOT_EXIST,
    SUPPORT_COMMAND_NOT_FOUND,
    COMMAND_SUPPORT_EXESIT,
    COMMAND_SUPPORT_CATEGORY_NOT_EXIST,
    AWB_SUPPORT_CATEGORY_NOT_EXIST,
    CLIENT_EXIST,
    HIGH_PRICE_NO_CONFIGURATION,
    POST_PARCEL_STATUS_FORBIDDEN,
    SUPPORT_AMEND_WEIGHT_NULL,
    CLIENT_ALREADY_EXIST,
    WEIGHT_MUST_BE_GT_ZERO,

    //    ---OFFLOAD---
    OFFLOAD_NOT_SUPPORT,
    OFFLOAD_BOX_QUANTITY,
    OFFLOAD_UPDATE_QUANTITY,
    COMMAND_NO_CLEARANCE_CHECK_CHARS_DIFFERENT,
    COMMAND_NO_CLEARANCE_CHECK_PREFIX_DIFFERENT,
    SHIPMENT_ROUTE_MATCH_RULE_EXIST,
    FILE_DOES_NOT_EXIST,

    SHIPMENT_ORDER_NOT_FOUND,
    TRY_AGAIN_LATER,

    //卡转
    TRANSFER_PRICING_COMMAND_ALREADY_EXISTED,
    // t1费用
    CHARTER_PRICING_ALREADY_EXIST,
    CHARTER_PRICING_NOT_EXIST,
    CHARTER_PRICING_DETAIL_NOT_EXIST,
    CHARTER_PRICING_DETAIL_NOT_UPDATE,
    THIS_CHARTER_PRICING_DETAIL_NOT_UPDATE,
    PLEASE_CONFIGURE_USER_PRICE,
    PLATE_QUANTITY_NOT_NULL,


    UPLOAD_FAIL,
    DOWNLOAD_FAIL,

    ORDER_FOLDER_NOT_EXISTS,
    ORDER_FOLDER_NOT_BE_SAME_NAME_SAME_WAREHOUSE_IN_THE_DAY,
    ORDER_FOLDER_ITEMS_TYPE_ERROR,
    ORDER_FOLDER_ITEMS_NOT_SAME_TYPE,
    ORDER_FORMAT_ERROR,
    ORDER_EXIST,
    ORDER_ENTER_MAXIMUM_QUANTITY_OUT_OF_RULE,
    ORDER_EX_STOCK_MAXIMUM_QUANTITY_OUT_OF_RULE,

    ITEMS_NOT_ALLOW_MULTI_TYPE,
    ITEMS_ORDER_FOLDER_TYPE_NOT_EXIST,
    ITEMS_T1_ARRIVE_TIME_NOT_ALLOW_EMPTY,


    PALLETS_ALEARDY_VERITY,
    PALLET_VERITY_FAIL,

    RECORD_CREATE_NOT_ALLOWED_BY_STATUS_Z,

    SUPPLIER_CHARAGE_TABLE_CAN_NOT_NULL,

    STATUS_BAD_TO_LAST,
    STATUS_BAD_TO_NEXT,

    ENTER_ULD_MAWB_NOT_EXIST,

    EX_STOCK_ULD_MAWB_NOT_EXIST,

    PRE_EX_STOCK_BOX_ID_NOT_CORRECT,
    EX_STOCK_BOX_ID_NOT_CORRECT,
    EX_STOCK_BOX_BATCH_ID_NOT_EXIST,
    EX_STOCK_BOX_OR_ITEM_NOT_EXIST,

    DELIVERY_TYPE_NOT_FOUND,
    UNITLOADDEVICE_NOT_FOUND,
    WEIGHT_LESS_THAN_ZERO,
    TRANSMISSION_NOT_FOUND,
    PDF_GENERATE_ERROR,


    TRANSFER_CENTER_NOT_FOUND,
    DELIVERY_TRANSFER_CENTER_CONFLICT,

    UNITLOADDEVICE_DELIVERED,
    BOX_DELIVERED,
    PALLET_DELIVERED,
    AKE_BAD_REQUEST_ERROR,
    AKE_INNER_ERROR,

    AMBIGUOUS_ID,
    REPEAT_CLOSE,
    CHANNEL_NOT_FOUND,
    CHANNEL_ALREADY_OPEN,
    PDA_CHANNEL_ID_NULL,
    PDA_PARAMS_NOT_FOUND,
    PALLET_NOT_IN_CHANNEL,
    CHANNEL_AND_PALLET_AMBIGUOUS,
    PDA_NO_MORE_SCAN,
    CHANNEL_NOT_ACTIVATE,
    UNIDENTIFIABLE_AND_CONTACT_TEAM_LEADER,

    PICK_UP_ROLLBACK_NOT_ALLOWED_CLEARANCE_TYPE,

    SWAP_BODY_COULD_NOT_OPERATION,
    NO_VALID_SWAP_BODY,

    AWB_NOT_EXIST,
    AWB_NOT_FOUND,
    TWB_NOT_FOUND,
    AWB_NOT_CLEARED,
    N0001,
    N0002,
    N0003,
    N0011,
    N0004,
    N0005,
    H7_MORE_THAN_150,


    //    ----
    EXIST_RECORD_BY_EMPLOYEE,
    UPLOAD_EMPLOYEE_PLACE_IS_NULL,
    UPLOAD_EMPLOYEE_PLACE_ERROR,
    UPLOAD_EMPLOYEE_NUMBER_IS_NULL,
    UPLOAD_EMPLOYEE_NUMBER_NOT_UNIQUE,
    UPLOAD_EMPLOYEE_POST_NOT_EQUAL,
    UPLOAD_EMPLOYEE_OVER_WORK,
    UPLOAD_EMPLOYEE_HAS_WORK,
    MODIFY_PLACE_ERROR,

    BATCH_QUANTITY_IS_ZERO,
    BATCH_QUANTITY_NOT_EQUAL_TOTAL,

    INSPECTION_PARCEL_WAREHOUSE_NOT_MATCH,

    EXIST_OPERATION_CONFIG
}
