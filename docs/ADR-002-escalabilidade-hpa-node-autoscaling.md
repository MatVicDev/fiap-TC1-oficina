# ADR-002 — Escalabilidade: HPA de pods + autoscaling de nós

**Status:** Aceita

## Contexto

A Fase 3 exige "Cluster Kubernetes com escalabilidade". A Fase 2 já tinha um `HorizontalPodAutoscaler` (HPA) escalando o Deployment da aplicação de 2 a 5 réplicas por CPU (`k8s/06-hpa.yaml`), mas isso só resolve metade do problema: se o cluster não tem capacidade de nó sobrando, o HPA cria pods que ficam `Pending`.

## Decisão

Manter o HPA existente (nível de pod) e adicionar, no Terraform de `fiap-tc3-infra-k8s`, um **node group gerenciado do EKS com autoscaling nativo** (`aws_eks_node_group.scaling_config`, min 2 / max 5 nós). As duas camadas trabalham juntas:

1. HPA aumenta réplicas quando a CPU dos pods passa de 70%.
2. Se os nós atuais não comportam os novos pods, o node group do EKS escala automaticamente (o EKS gerencia isso nativamente para node groups gerenciados, sem precisar operar o Cluster Autoscaler como um add-on separado).

## Por que não Karpenter

Karpenter é mais flexível (escolhe tipo de instância dinamicamente) mas adiciona complexidade operacional (outro componente para instalar/manter) desproporcional ao porte deste desafio. Node group gerenciado com autoscaling nativo resolve o requisito com uma linha de configuração Terraform.

## Consequência

Escalar de verdade depende de dois recursos aplicados nos dois repositórios corretos: `fiap-tc3-infra-k8s` (capacidade de nó) e `fiap-TC1-oficina/k8s/06-hpa.yaml` (réplicas de pod). Documentado aqui para não ser esquecido ao revisar só um dos dois repositórios.
