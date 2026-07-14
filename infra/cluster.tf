resource "kind_cluster" "oficina" {
  name           = var.cluster_name
  wait_for_ready = true

  kind_config {
    kind        = "Cluster"
    api_version = "kind.x-k8s.io/v1alpha4"

    node {
      role = "control-plane"

      # Mapeia as portas do host para os NodePort já usados em k8s/05-app.yaml (30080)
      # e k8s/04-mailhog.yaml (30825), mantendo as mesmas URLs do docker-compose.
      extra_port_mappings {
        container_port = 30080
        host_port       = 8080
      }

      extra_port_mappings {
        container_port = 30825
        host_port       = 8025
      }
    }
  }
}
