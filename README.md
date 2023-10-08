```bash
asdf plugin add jbang
asdf list all jbang
asdf install jbang 0.111.0
asdf global jbang 0.111.0
asdf local jbang 0.111.0
jbang init -t cli BackupRestore.java
jbang BackupRestore.java # run
jbang BackupRestore.java --help
jbang BackupRestore.java mhagnumdw

upx -7 -k BackupRestore # decrease executable size
```

```bash
asdf install java graalvm-community-17.0.8
asdf local java graalvm-community-17.0.8
```

```bash
# to install wrapper, like maven wrapper, so you don't
# need to have JBang installed
jbang wrapper install

./jbang BackupRestore.java mhagnumdw
```
