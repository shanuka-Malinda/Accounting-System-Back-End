package com.example.NFX.Constant;

public class CommonMsg {
    // ========================================
    // AUTHENTICATION & AUTHORIZATION MESSAGES
    // ========================================
    public static final String LOGIN_SUCCESS = "Login successful";
    public static final String LOGIN_FAILED = "Invalid username or password";
    public static final String LOGOUT_SUCCESS = "Logged out successfully";
    public static final String SESSION_EXPIRED = "Your session has expired. Please log in again";
    public static final String ACCESS_DENIED = "Access denied. Insufficient permissions";
    public static final String ACCOUNT_LOCKED = "Account has been locked due to multiple failed login attempts";
    public static final String PASSWORD_CHANGED = "Password changed successfully";
    public static final String PASSWORD_MISMATCH = "Passwords do not match";
    public static final String WEAK_PASSWORD = "Password must contain at least 8 characters with uppercase, lowercase, and numbers";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access attempt detected";

    // ========================================
    // GENERAL VALIDATION MESSAGES
    // ========================================
    public static final String EMPTY_NAME = "Name is required";
    public static final String EMPTY_FIRSTNAME = "First name is required";
    public static final String EMPTY_LASTNAME = "Last name is required";
    public static final String EMPTY_EMAIL = "Email address is required";
    public static final String INVALID_EMAIL = "Please enter a valid email address";
    public static final String EMPTY_PHONE = "Phone number is required";
    public static final String INVALID_PHONE = "Please enter a valid phone number";
    public static final String EMPTY_ADDRESS = "Address is required";
    public static final String EMPTY_USERNAME = "Username is required";
    public static final String EMPTY_PASSWORD = "Password is required";
    public static final String INVALID_DATE_FORMAT = "Please enter a valid date in DD/MM/YYYY format";
    public static final String FUTURE_DATE_NOT_ALLOWED = "Future dates are not allowed";
    public static final String INVALID_FILE_FORMAT = "Invalid file format. Please upload a valid file";
    public static final String FILE_SIZE_EXCEEDED = "File size exceeds the maximum limit of 10MB";
    public static final String REQUIRED_FIELD = "This field is required";

    // ========================================
    // SALES INVOICE VALIDATION MESSAGES
    // ========================================
    public static final String EMPTY_INVOICE_NO = "Invoice number is required";
    public static final String EMPTY_CUSTOMER_NAME = "Customer name is required";
    public static final String EMPTY_ITEM_CODE = "Item code is required";
    public static final String EMPTY_DESCRIPTION = "Description is required";
    public static final String EMPTY_QTY = "Quantity is required";
    public static final String EMPTY_UNIT_PRICE = "Unit price is required";
    public static final String EMPTY_AMOUNT = "Amount is required";
    public static final String EMPTY_PAYMENT_METHOD = "Payment method is required";
    public static final String EMPTY_INVOICE_DATE = "Invoice date is required";
    public static final String EMPTY_DUE_DATE = "Due date is required";

    // ========================================
    // SALES INVOICE BUSINESS RULE MESSAGES
    // ========================================
    public static final String INVALID_QTY = "Quantity must be greater than zero";
    public static final String INVALID_UNIT_PRICE = "Unit price must be greater than zero";
    public static final String INVALID_AMOUNT = "Amount must be greater than zero";
    public static final String DUPLICATE_INVOICE_NO = "Invoice number already exists";
    public static final String DUPLICATE_INVOICE_IN_REQUEST = "Duplicate invoice numbers found in the request";
    public static final String INVOICE_DATE_FUTURE = "Invoice date cannot be in the future";
    public static final String DUE_DATE_BEFORE_INVOICE = "Due date cannot be before invoice date";
    public static final String INVOICE_ALREADY_PAID = "Invoice has already been marked as paid";
    public static final String INVOICE_AMOUNT_MISMATCH = "Invoice total does not match sum of line items";

    // ========================================
    // CUSTOMER MANAGEMENT MESSAGES
    // ========================================
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String DUPLICATE_CUSTOMER_EMAIL = "Customer with this email already exists";
    public static final String CUSTOMER_SAVED = "Customer saved successfully";
    public static final String CUSTOMER_UPDATED = "Customer updated successfully";
    public static final String CUSTOMER_DELETED = "Customer deleted successfully";
    public static final String CUSTOMER_HAS_INVOICES = "Cannot delete customer with existing invoices";

    // ========================================
    // PRODUCT/INVENTORY MESSAGES
    // ========================================
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String INSUFFICIENT_STOCK = "Insufficient stock available";
    public static final String PRODUCT_SAVED = "Product saved successfully";
    public static final String PRODUCT_UPDATED = "Product updated successfully";
    public static final String PRODUCT_DELETED = "Product deleted successfully";
    public static final String DUPLICATE_PRODUCT_CODE = "Product code already exists";
    public static final String STOCK_UPDATED = "Stock updated successfully";

    // ========================================
    // SALES INVOICE SUCCESS MESSAGES
    // ========================================
    public static final String INVOICE_NOT_FOUND = "Sales invoice not found";
    public static final String SALES_INVOICE_SAVED = "Sales invoice saved successfully";
    public static final String BULK_SALES_INVOICES_SAVED = "Multiple sales invoices saved successfully";
    public static final String SALES_INVOICE_UPDATED = "Sales invoice updated successfully";
    public static final String SALES_INVOICE_DELETED = "Sales invoice deleted successfully";
    public static final String INVOICE_PAYMENT_RECORDED = "Payment recorded successfully";
    public static final String INVOICE_SENT_TO_CUSTOMER = "Invoice sent to customer successfully";
    public static final String INVOICE_CANCELLED = "Invoice cancelled successfully";

    // ========================================
    // SYSTEM ERROR MESSAGES
    // ========================================
    public static final String SYSTEM_ERROR = "An unexpected system error occurred. Please try again";
    public static final String DATABASE_CONNECTION_ERROR = "Database connection failed. Please contact support";
    public static final String NETWORK_ERROR = "Network error occurred. Please check your connection";
    public static final String SERVICE_UNAVAILABLE = "Service temporarily unavailable. Please try again later";
    public static final String TIMEOUT_ERROR = "Request timed out. Please try again";
    public static final String DATA_INTEGRITY_ERROR = "Data integrity constraint violation";
    public static final String CONCURRENT_MODIFICATION = "Record was modified by another user. Please refresh and try again";
    public static final String BACKUP_IN_PROGRESS = "System backup in progress. Some features may be temporarily unavailable";

    // ========================================
    // SYSTEM SUCCESS MESSAGES
    // ========================================
    public static final String OPERATION_SUCCESSFUL = "Operation completed successfully";
    public static final String DATA_IMPORTED = "Data imported successfully";
    public static final String DATA_EXPORTED = "Data exported successfully";
    public static final String BACKUP_CREATED = "System backup created successfully";
    public static final String SETTINGS_SAVED = "Settings saved successfully";
    public static final String EMAIL_SENT = "Email sent successfully";
    public static final String REPORT_GENERATED = "Report generated successfully";

    // ========================================
    // USER MANAGEMENT MESSAGES
    // ========================================
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_CREATED = "User created successfully";
    public static final String USER_UPDATED = "User updated successfully";
    public static final String USER_DELETED = "User deleted successfully";
    public static final String USER_ACTIVATED = "User account activated successfully";
    public static final String USER_DEACTIVATED = "User account deactivated successfully";
    public static final String DUPLICATE_USERNAME = "Username already exists";
    public static final String DUPLICATE_EMAIL = "Email address already exists";

    // ========================================
    // PAYMENT MESSAGES
    // ========================================
    public static final String PAYMENT_SUCCESSFUL = "Payment processed successfully";
    public static final String PAYMENT_FAILED = "Payment processing failed";
    public static final String INVALID_PAYMENT_METHOD = "Invalid payment method selected";
    public static final String PAYMENT_AMOUNT_INVALID = "Payment amount must be greater than zero";
    public static final String PARTIAL_PAYMENT_RECORDED = "Partial payment recorded successfully";
    public static final String OVERPAYMENT_DETECTED = "Payment amount exceeds invoice total";

    // ========================================
    // NOTIFICATION MESSAGES
    // ========================================
    public static final String NOTIFICATION_SENT = "Notification sent successfully";
    public static final String EMAIL_DELIVERY_FAILED = "Email delivery failed";
    public static final String SMS_SENT = "SMS sent successfully";
    public static final String SMS_DELIVERY_FAILED = "SMS delivery failed";

    // ========================================
    // CONFIRMATION MESSAGES
    // ========================================
    public static final String CONFIRM_DELETE = "Are you sure you want to delete this record?";
    public static final String CONFIRM_CANCEL = "Are you sure you want to cancel this operation?";
    public static final String CONFIRM_LOGOUT = "Are you sure you want to log out?";
    public static final String UNSAVED_CHANGES = "You have unsaved changes. Do you want to save before leaving?";

    // ========================================
    // VALIDATION SUMMARY MESSAGES
    // ========================================
    public static final String VALIDATION_FAILED = "Please correct the following errors and try again";
    public static final String REQUIRED_FIELDS_MISSING = "Please fill in all required fields";
    public static final String INVALID_DATA_FORMAT = "Please check the data format and try again";

    public static final String EMPTY_ACCOUNT_NAME="Account name is required";
    public static final String EMPTY_ACCOUNT_NO = "Account Number is Empty!";
    public static final String EMPTY_REFERENCE = "Reference is Empty!";
    public static final String EMPTY_CRDR = "Credit Debit Status is Empty";
    public static final String EXITED_ACC_NAME = "Account Name Exited";
}
