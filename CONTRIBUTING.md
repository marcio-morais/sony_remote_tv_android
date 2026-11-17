# Contribuindo / Contributing

Obrigado por querer ajudar! / Thanks for helping out. Issues and pull requests can be written in Portuguese or English.

## O que mais ajuda / What helps most

O projeto foi testado **apenas numa Sony Bravia KDL-42W655A**. A contribuição mais valiosa é dizer o que funciona (ou não) no seu modelo:

- confirmar os códigos de teclas (SEN, Opções, Jump, cores, mídia, números);
- relatar como o seu modelo liga e desliga (alternar, desligar discreto, Wake-on-LAN);
- corrigir ou acrescentar códigos IRCC / SIRC;
- melhorar a interface ou a busca da TV na rede.

Para relatar um modelo, abra uma issue com o modelo **"Relatar modelo de TV"**.

## Rodando o projeto

Abra a pasta no Android Studio, ou:

```bash
./gradlew assembleDebug     # JDK 17; o APK sai em app/build/outputs/apk/debug/
```

Para testar você precisa de uma TV Sony Bravia na mesma rede, com **Controle IP** ativado (Configurações > Rede > Controle IP, autenticação "Normal e Pre-Shared Key").

## Pull requests

1. Faça um fork e crie uma branch a partir da `main`.
2. Mantenha a mudança pequena e focada em um assunto.
3. Código, nomes e comentários em inglês; textos da interface em português.
4. Diga no pull request **em qual modelo de TV você testou** e o que viu acontecer.
5. Não inclua dados pessoais (IP, MAC, chaves) em código, capturas de tela ou logs.

O CI compila o app a cada pull request. Se falhar por erro de rede ao baixar dependências (HTTP 429), é passageiro: peça para reexecutar.
