package com.filip.smartlifesaver.model;


import lombok.Data;

@Data
public class FirebaseDataMessageDTO {


    private String to;

    private FirebaseDataContainer data;

    public FirebaseDataMessageDTO() {

    }


    @Data
    public static class FirebaseDataContainer {

        private String notificationMsg;

        public FirebaseDataContainer() {

        }

    }

}
