Projeto View Assinatura Digital
==========================================

Projeto responsável por assinar digitalmente os documentos comprovantes de vistoria fisica do PMN.
O projeto não faz parte do projeto vistoria fisica master, deve ser importado posteriormente como maven project. 

Comandos para conhecimento:
	
1) Assinar JAR da aplicação: 
(da pasta raiz do módulo; utiliza o arquivo JKS anexado ao projeto)
jarsigner -keystore pmn_vf.jks -storepass 123456 target\vf-view-assinatura-digital-2.0.0.5.jar pmnvistoriafisica 

2) Copiar JAR assinado para projeto View-Interno:
(da pasta raiz do módulo)
copy target\vf-view-assinatura-digital-2.0.0.5.jar E:\ProjetosSVN\Branch_PMN_CERTDIG_20120425\pmn-vistoria-fisica\vf-view-interno\src\main\webapp\resources\lib