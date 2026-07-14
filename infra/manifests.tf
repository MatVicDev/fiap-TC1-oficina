resource "null_resource" "apply_manifests" {
  depends_on = [kind_cluster.oficina]

  triggers = {
    manifests_hash = sha1(join("", [
      for f in fileset("${path.module}/../k8s", "*.yaml") : filesha1("${path.module}/../k8s/${f}")
    ]))
    kubeconfig_path = kind_cluster.oficina.kubeconfig_path
  }

  provisioner "local-exec" {
    command = "kubectl apply -f ${path.module}/../k8s --kubeconfig ${self.triggers.kubeconfig_path}"
  }

  provisioner "local-exec" {
    when    = destroy
    command = "kubectl delete -f ${path.module}/../k8s --kubeconfig ${self.triggers.kubeconfig_path} --ignore-not-found=true"
  }
}
