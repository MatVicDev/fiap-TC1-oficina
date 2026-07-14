package br.com.oficina.domain.notificacao;

public interface NotificacaoPort {
    void notificar(String destinatario, String assunto, String mensagem);
}
