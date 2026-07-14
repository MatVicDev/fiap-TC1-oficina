output "cluster_name" {
  description = "Nome do cluster kind criado"
  value       = kind_cluster.oficina.name
}

output "kubeconfig_path" {
  description = "Caminho do kubeconfig gerado para acessar o cluster"
  value       = kind_cluster.oficina.kubeconfig_path
}

output "app_url" {
  description = "URL da API da oficina"
  value       = "http://localhost:8080"
}

output "mailhog_url" {
  description = "URL da interface web do MailHog"
  value       = "http://localhost:8025"
}
