package bancolombia.dtd.vd.sucursales.api.datosCliente.dto;

import java.io.Serializable;

public class SaveReponse implements Serializable {

    private static final long serialVersionUID = -7459224536650697439L;
    private String code;
    private boolean isSuccess;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public SaveReponse(String code, boolean isSuccess) {
        this.code = code;
        this.isSuccess = isSuccess;
    }

    public SaveReponse() {
    }
}
