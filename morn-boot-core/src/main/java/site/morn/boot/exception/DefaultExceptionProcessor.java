package site.morn.boot.exception;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.exception.ApplicationMessage;
import site.morn.exception.ExceptionInterpreter;
import site.morn.exception.ExceptionProcessor;

/**
 * 默认异常处理器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/10/17
 */
@AllArgsConstructor
@Slf4j
public class DefaultExceptionProcessor implements ExceptionProcessor {

  /**
   * 异常解释器缓存
   */
  private final IdentifiedBeanCache beanCache;

  @Override
  public <T extends Exception> ApplicationMessage process(T exception) {
    // 从缓存中获取异常解释器
    ExceptionInterpreter exceptionInterpreter = beanCache
        .targetBean(ExceptionInterpreter.class, exception.getClass());
    if (Objects.isNull(exceptionInterpreter)) {
      log.debug("异常处理失败：尚未发现处理{}的异常解释器", exception.getClass().getSimpleName());
      return null;
    }
    return exceptionInterpreter.resolve(exception);
  }
}
