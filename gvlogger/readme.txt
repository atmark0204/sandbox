ダウンロード

 32bit Windows (動作未確認)

  https://github.com/atmark0204/sandbox/raw/master/gvlogger/bin/gvlogger-0.0.1-SNAPSHOT-x86.zip

 64bit Windows

  https://github.com/atmark0204/sandbox/raw/master/gvlogger/bin/gvlogger-0.0.1-SNAPSHOT-x86_64.zip

必要要件

 １．JRE7以降がインストールされていること

  http://java.com/ja/download/

 ２．環境変数 JAVA_HOME が設定されていること

 ３．WinPcapがインストールされていること

  http://www.winpcap.org/

動作モード

 １．on-line

  通常のキャプチャモード

 ２．off-line

  *.pcap形式のファイルを読み込む
  ※ 2バイト文字を含むパスからは読み込み出来ません(ライブラリの仕様（バグ）の可能性の為修正予定なし)

 ３．dump

  *.pcap形式のファイルを出力する（保存後off-lineモードで閲覧可能）
  ※ 2バイト文字を含むパスには保存出来ません(ライブラリの仕様（バグ）の可能性の為修正予定なし)