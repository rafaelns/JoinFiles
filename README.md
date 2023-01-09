# Descrição:

Essa é uma simples aplicação que lê arquivos em um determinado diretório e como resultado, 
gera um novo arquivo com o conteúdo mesclado dos demais arquivos que foram lidos.

A principal motivação foi para juntar arquivos CSV preservando o cabeçalho do arquivo
que é configurado em um arquivo de properties (resources/application.properties).

Para rodar a aplicação é necessário informar propriedades como:
1 - directory.read.url=[diretório que estão os arquivos CSV para leitura]
2 - directory.joined.url=[diretório e nome do arquivo que será gerado. ex: desktop/file/result.csv]
3 - file.header=[padrão de header a ser gravado no primeiro arquivo]


