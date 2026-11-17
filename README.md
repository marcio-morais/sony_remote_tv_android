# Sony Remote (Android)

App Android em Kotlin e Jetpack Compose para controlar uma TV **Sony Bravia**. Funciona sozinho no celular, sem servidor: envia os comandos por Wi-Fi (protocolo IRCC da Sony) ou, se o celular tiver emissor infravermelho, por IR (SIRC).

> **English:** a standalone Android remote for Sony Bravia TVs (Kotlin + Jetpack Compose). It sends keys over Wi-Fi (Sony IRCC) or infrared (SIRC) when the phone has an IR blaster, finds the TV on your network automatically and can wake it from standby with Wake-on-LAN. Tested only on a **KDL-42W655A**; other Bravia models with "IP Control" should work but are untested. Contributions for other models are welcome. See the Portuguese sections below for install and setup.

O projeto nasceu porque o controle da minha TV quebrou e o app da Sony foi descontinuado. O protótipo web que serviu de referência de layout, com um servidor local em Python, fica em [sony_remote_tv_web](https://github.com/marcio-morais/sony_remote_tv_web).

## Compatibilidade

Funciona com TVs **Sony Bravia** que tenham "Controle IP" nas configurações de rede. Foi testado **apenas na KDL-42W655A**. Outros modelos Bravia devem funcionar, mas não foram testados. TVs de outras marcas (LG, Samsung, TCL...) usam outros protocolos e não funcionam com este app.

Este projeto não é afiliado à Sony. "Sony" e "Bravia" são marcas da Sony Group Corporation.

## Instalar no celular

Cada push na `main` gera um APK pelo GitHub Actions. No celular, abra o link, baixe e instale (o Android vai pedir para permitir a instalação de apps desta fonte):

```
https://github.com/marcio-morais/sony_remote_tv/releases/latest/download/SonyRemote.apk
```

O APK é assinado com a chave de debug do build, que muda a cada versão. Por isso o Android pode recusar uma atualização por cima; nesse caso, desinstale o app antes de instalar o novo. Em **Configurações (engrenagem) > Verificar atualização** o app compara a sua versão com a última publicada.

## O que o app faz

Duas abas, que também mudam com um arrasto de dedo: **Controle** (Entrada, Home, SEN, Liga, setas e OK, Mudo, volume, canal, Opções, Voltar, Sair e Jump) e **Números** (0 a 9, teclas coloridas e mídia). Segurar setas, volume ou canal repete o comando.

A engrenagem abre a conexão: busca automática da TV na rede (SSDP e varredura da faixa local), IP, chave PSK, MAC da TV, o modo de envio (Automático, Infravermelho ou Wi-Fi), a versão do app e o histórico dos últimos comandos. O nome da TV aparece no topo.

No modo Automático o app usa IR quando o celular tem emissor e a tecla tem código SIRC, e Wi-Fi no resto.

**Ligar e desligar por Wi-Fi.** Na KDL-42W655A o código de alternar liga/desliga é ignorado: a TV só obedece ao código de desligar, e em standby só acorda com Wake-on-LAN. Por isso, por Wi-Fi, o botão Liga consulta o estado da TV, desliga se ela estiver ligada e envia o pacote Wake-on-LAN se estiver em standby. O app também acorda a TV ao ser aberto. Para isso o campo **MAC da TV** precisa estar preenchido (o app tenta preencher sozinho; confira). Outros modelos podem se comportar diferente.

## Configurar a TV

Configurações > Rede > Controle IP, com autenticação "Normal e Pre-Shared Key" e a mesma chave informada no app (padrão `0000`). Os nomes dos menus variam entre gerações. Para o Wake-on-LAN, a TV precisa ter a opção de ligar pela rede ativada, quando o modelo oferecer.

## Estrutura

```
app/src/main/java/app/sonyremote/tv/
  SonyKey.kt            teclas com código IRCC (Wi-Fi) e SIRC (IR)
  SonyTvClient.kt       envio IRCC por HTTP (OkHttp)
  SonyIrRemote.kt       quadro SIRC de 12 bits no emissor IR
  TvRemote.kt           escolhe IR ou Wi-Fi conforme o modo
  TvPower.kt            estado de energia, desligar e Wake-on-LAN
  TvDiscovery.kt        busca da TV (SSDP + varredura /24)
  UpdateChecker.kt      verificação de nova versão no GitHub
  WifiNetworkHelper.kt  fixa o tráfego na rede Wi-Fi (mesmo com dados móveis ligados)
  Settings.kt           IP, nome, PSK, MAC e modo salvos no DataStore
  ui/                   ViewModel e telas em Compose
.github/workflows/build.yml   gera o APK a cada push
```

## Compilar localmente

Abra a pasta no Android Studio, ou rode `./gradlew assembleDebug` (JDK 17). O APK sai em `app/build/outputs/apk/debug/`.

## Contribuir

Contribuições são bem-vindas, principalmente para outros modelos Bravia: confirmar códigos de teclas, ajustar o comportamento de ligar/desligar e relatar o que funciona no seu modelo. Abra uma issue contando o modelo da TV e o que aconteceu, ou envie um pull request.

## Pendências

- Os códigos de SEN, Opções, Sair, Jump, números, cores e mídia ainda precisam ser validados na TV real.
- Os comandos IR das teclas de navegação foram derivados do código IRCC e não foram testados.
- O APK usa chave de debug; para publicar na Play Store seria preciso uma chave própria.

## Licença

[MIT](LICENSE)
