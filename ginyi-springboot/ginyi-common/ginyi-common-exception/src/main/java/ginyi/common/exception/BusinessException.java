package ginyi.common.exception;

import ginyi.common.result.StateCode;
import lombok.Data;

/**
 * 业务异常处理
 */
@Data
public class BusinessException extends RuntimeException{

    private StateCode state;
    private Object data;

    public BusinessException(StateCode state, Object data) {
        this.state = state;
        this.data = data;
    }

    public BusinessException(StateCode state) {
        this.state = state;
        this.data = data;
    }

}
