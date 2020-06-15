package forumService.forumService.Exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ForumServiceExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {
		final String mensagemUsuário = messageSource.getMessage("recurso.operacao-nao-permitida", null,
				LocaleContextHolder.getLocale());
		final String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuário, mensagemDesenvolvedor));
		return super.handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		final String mensagemUsuário = messageSource.getMessage("mensagem.invalida", null,
				LocaleContextHolder.getLocale());
		final String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		return super.handleExceptionInternal(ex, new Erro(mensagemUsuário, mensagemDesenvolvedor), headers,
				HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		final List<Erro> erros = criaListaDeErros(ex.getBindingResult());
		return super.handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ IllegalArgumentException.class, MismatchedInputException.class })
	public ResponseEntity<Object> handleIllegalArgumentException(EntityNotFoundException ex, WebRequest request) {
		final String mensagemUsuário = messageSource.getMessage("recurso.não-encontrado", null,
				LocaleContextHolder.getLocale());
		final String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuário, mensagemDesenvolvedor));
		return super.handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({ EntityNotFoundException.class })
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
		final String mensagemUsuário = messageSource.getMessage("recurso.não-encontrado", null,
				LocaleContextHolder.getLocale());
		final String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuário, mensagemDesenvolvedor));
		return super.handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	@ExceptionHandler({AccessDeniedException.class})
	public ResponseEntity<Object> handleEntityAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		final String mensagemUsuário = messageSource.getMessage("recurso.não-autorizado", null,
				LocaleContextHolder.getLocale());
		final String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuário, mensagemDesenvolvedor));
		return super.handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
	}

	private List<Erro> criaListaDeErros(final BindingResult bindingResult) {
		final List<Erro> erros = new ArrayList<>();
		for (final FieldError fieldError : bindingResult.getFieldErrors()) {
			final String mensagemUsuário = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			final String mensagemDesenvolvedor = fieldError.toString();
			erros.add(new Erro(mensagemUsuário, mensagemDesenvolvedor));
		}
		return erros;
	}

	public static class Erro {
		private final String mensagemUsuário;
		private final String mensagemDesenvolvedor;

		public Erro(final String mensagemUsuário, final String mensagemDesenvolvedor) {
			this.mensagemUsuário = mensagemUsuário;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuário() {
			return mensagemUsuário;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}
	}

}