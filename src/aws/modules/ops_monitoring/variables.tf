variable "env" {
  description = "environment reference"
}

variable "monitored_buckets" {
  type = list(string)
}

variable "cloudfront_upload_id" {}

variable "cloudfront_distribution_id" {}

variable "cloudfront_submission_id" {}

variable "request_triggered" {
  type = list(string)
}

variable "gateways" {
  type = list(string)
}

variable "analytics_submission_function" {
  description = "name of lambda function"
}

variable "diagnosis_keys_submission_function" {
  description = "name of lambda function"
}

variable "exposure_notification_circuit_breaker_function" {
  description = "name of lambda function"
}

variable "diagnosis_keys_processing_function" {
  description = "name of lambda function"
}

variable "federation_keys_processing_upload_function" {
  description = "name of lambda function"
}

variable "federation_keys_processing_download_function" {
  description = "name of lambda function"
}

variable "risky_post_districts_upload_function" {
  description = "name of lambda function"
}

variable "risky_venues_circuit_breaker_function" {
  description = "name of lambda function"
}

variable "risky_venues_upload_function" {
  description = "name of lambda function"
}

variable "virology_submission_function" {
  description = "name of lambda function"
}

variable "virology_upload_function" {
  description = "name of lambda function"
}

variable "isolation_payment_order_function" {
  description = "name of lambda function"
}

variable "isolation_payment_verify_function" {
  description = "name of lambda function"
}

variable "isolation_payment_consume_function" {
  description = "name of lambda function"
}

variable "advanced_analytics_function" {
  description = "name of lambda function"
}

variable "virology_submission_api_gateway_id" {
  description = "id of virology submission api gateway"
}

variable "virology_upload_api_gateway_id" {
  description = "id of virology upload api gateway"
}

variable "shield_protected_arns" {
  description = "ARN of resources protected by Shield Advanced"
  type        = list(string)
}

variable "shield_alarm_set_topic_arn" {
  description = "ARN of SNS topic subscribed to shield alarms ALARM state"
  type        = string
}

variable "shield_alarm_ok_topic_arn" {
  description = "ARN of SNS topic subscribed to shield alarms OK state"
  type        = string
}

variable "tags" {
  description = "A map of key-value labels used to tag AWS resources"
  type        = map(string)
}
