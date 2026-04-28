package lk.ijse._2_back_end.util;


import lombok.Data;


@Data
public class APIResponse<T>{
    private int status;
    private String message;
    private T data;


    public APIResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
