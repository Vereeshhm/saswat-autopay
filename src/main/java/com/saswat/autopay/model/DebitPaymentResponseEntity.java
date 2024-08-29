//package com.saswat.autopay.model;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "debit_payment_response")
//public class DebitPaymentResponseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", updatable = false, nullable = false)
//    private Long id;
//
//    @Embedded
//    private Result result;
//
//    // Getters and setters
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Result getResult() {
//        return result;
//    }
//
//    public void setResult(Result result) {
//        this.result = result;
//    }
//
//    @Embeddable
//    public static class Result {
//
//        @Column(name = "txnId", nullable = false)
//        private String txnId;
//
//        @Column(name = "amount", nullable = false)
//        private String amount;
//
//        @Column(name = "productInfo", nullable = false)
//        private String productInfo;
//
//        @Column(name = "firstName", nullable = false)
//        private String firstName;
//
//        @Column(name = "phone", nullable = false)
//        private String phone;
//
//        @Column(name = "email", nullable = false)
//        private String email;
//
//        @Column(name = "customerAuthenticationId", nullable = false, unique = true)
//        private String customerAuthenticationId;
//
//        @Column(name = "autoDebitAccessKey", nullable = false)
//        private String autoDebitAccessKey;
//
//        @Column(name = "signzyId", nullable = false)
//        private String signzyId;
//
//        @Column(name = "merchantDebitId", nullable = false)
//        private String merchantDebitId;
//
//        @Embedded
//        private Data data;
//
//        // Getters and setters
//
//        public String getTxnId() {
//            return txnId;
//        }
//
//        public void setTxnId(String txnId) {
//            this.txnId = txnId;
//        }
//
//        public String getAmount() {
//            return amount;
//        }
//
//        public void setAmount(String amount) {
//            this.amount = amount;
//        }
//
//        public String getProductInfo() {
//            return productInfo;
//        }
//
//        public void setProductInfo(String productInfo) {
//            this.productInfo = productInfo;
//        }
//
//        public String getFirstName() {
//            return firstName;
//        }
//
//        public void setFirstName(String firstName) {
//            this.firstName = firstName;
//        }
//
//        public String getPhone() {
//            return phone;
//        }
//
//        public void setPhone(String phone) {
//            this.phone = phone;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public String getCustomerAuthenticationId() {
//            return customerAuthenticationId;
//        }
//
//        public void setCustomerAuthenticationId(String customerAuthenticationId) {
//            this.customerAuthenticationId = customerAuthenticationId;
//        }
//
//        public String getAutoDebitAccessKey() {
//            return autoDebitAccessKey;
//        }
//
//        public void setAutoDebitAccessKey(String autoDebitAccessKey) {
//            this.autoDebitAccessKey = autoDebitAccessKey;
//        }
//
//        public String getSignzyId() {
//            return signzyId;
//        }
//
//        public void setSignzyId(String signzyId) {
//            this.signzyId = signzyId;
//        }
//
//        public String getMerchantDebitId() {
//            return merchantDebitId;
//        }
//
//        public void setMerchantDebitId(String merchantDebitId) {
//            this.merchantDebitId = merchantDebitId;
//        }
//
//        public Data getData() {
//            return data;
//        }
//
//        public void setData(Data data) {
//            this.data = data;
//        }
//
//        // Data class embedded within Result
//        @Embeddable
//        public static class Data {
//
//            @Column(name = "status", nullable = false)
//            private int status;
//
//            @Column(name = "data", nullable = false)
//            private String data;
//
//            // Getters and setters
//
//            public int getStatus() {
//                return status;
//            }
//
//            public void setStatus(int status) {
//                this.status = status;
//            }
//
//            public String getData() {
//                return data;
//            }
//
//            public void setData(String data) {
//                this.data = data;
//            }
//        }
//    }
//}
