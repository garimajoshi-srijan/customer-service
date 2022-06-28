package mvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;

    public class Error {

        private String message;
        private HttpStatus status_code;

        public Error(String message, HttpStatus status_code) {
            super();
            this.message = message;
            this.status_code = status_code;
        }

        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public HttpStatus getStatus_code() {
            return status_code;
        }
        public void setStatus_code(HttpStatus status_code) {
            this.status_code = status_code;
        }


    }



