# Platform Consumer Kafka em Java

### Kafka Drop ###
Kafdrop is a web UI for viewing Kafka topics and browsing consumer groups. The tool displays information such as brokers, topics, partitions, consumers, and lets you view messages.

http://localhost:9000/

### Docker Compose ###
Compose is a tool for defining and running multi-container Docker applications.

### O que é o Apache Kafka? ###

O Apache Kafka é uma plataforma open source que trabalha com streaming de eventos de forma distribuída. Esse sistema de mensageria vem sendo muito utilizado no mercado devido a sua escalabilidade e tolerância a falha.





### Como o Kafka funciona? ###
Diferentemente do RabbitMQ, que trabalha com filas, o Kafka usa um conceito chamado de tópicos. Os tópicos são os responsáveis por armazenar as mensagens. Cada tópico é separado por partições. Essas partições ajudam a otimizar o processo de leitura das mensagens.

Na imagem abaixo, podemos ver os producers, que são os sistemas que produzem as mensagens e as envia para o tópico. O Kafka, por padrão, define em qual partição cada mensagem será enviada. Temos também os consumers que são os sistemas que vão consumir essas mensagens e processá-las. Nessa imagem podemos ver também que o Kafka trabalha com clusters. Um cluster é um conjunto de máquinas executando o Apache Kafka e cada uma dessas máquinas é um broker.

![](https://media.licdn.com/dms/image/D4D12AQF9GZdh5dD6Xg/article-inline_image-shrink_1500_2232/0/1677190718863?e=1696464000&v=beta&t=upUtnmSeBNoy39nrTz9a1FtSTtZMy9Gr6619_-zCko0)

As mensagens não são descartadas, elas ficam armazenadas no Kafka, mesmo após elas terem sido processadas pelo consumer. Cada mensagem dentro de uma partição é numerada por um offset, conforme a ordem que chegam, como mostra a imagem abaixo:

<img src="https://media.licdn.com/dms/image/D4D12AQHCjPVymnBjWA/article-inline_image-shrink_1500_2232/0/1677190869293?e=1696464000&amp;v=beta&amp;t=2oSL__UW3CtsaI2P0ZNHQ2Frw3NMlDuAzwsLqgUk6Ys" width="300" height="100" />



### Grupos de Consumo ### 

Os consumidores são organizados em grupos de consumo. Se por exemplo temos três consumidores registrados no mesmo grupo e que estão consumindo um tópico que possui três partições, cada consumidor ficará responsável por ouvir uma partição.

<img src="https://media.licdn.com/dms/image/D4D12AQHy766N1sGWNg/article-inline_image-shrink_1500_2232/0/1677190977040?e=1696464000&amp;v=beta&amp;t=L7D_jXpfQSE7AdfyGwfyU-sluY8HACHriI-5HVWLy98" width="450" height="240" />

Se tivéssemos mais um consumidor neste mesmo grupo, ele não seria associado a nenhuma partição, ou seja, ele ficaria ocioso, como mostra a imagem abaixo. Por isso, a quantidade de consumidores deve ser igual ou menor do que a de partições.

<img src="https://media.licdn.com/dms/image/D4D12AQHV_IjgvdxUBg/article-inline_image-shrink_1500_2232/0/1677191047089?e=1696464000&amp;v=beta&amp;t=Wv4E-U6MbanyLdZX5zTJSzqNa7fsugdLAFZtpNYp2Ok" width="400" height="300" />


Caso o número de consumidores seja menor que o de partições, o Kafka associará mais de uma partição para o mesmo consumidor:

<img src="https://media.licdn.com/dms/image/D4D12AQFPsdGiOAH0XQ/article-inline_image-shrink_1500_2232/0/1677191115387?e=1696464000&amp;v=beta&amp;t=as-DGI91LC92u-tnZX9pqp35SfNyZNSl2lEtf-xCtqM" width="350" height="200" />

Referências:
https://www.linkedin.com/pulse/exemplo-de-producer-e-consumer-kafka-com-spring-boot-soret-lahoud/?originalSubdomain=pt