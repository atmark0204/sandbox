ダウンロード

 64bit Windows

  bin/gvlogger-*-x86_64.zip

必要要件

 1. JRE8以降がインストールされていること

  http://java.com/ja/download/

 2. WinPcapがインストールされていること

  http://www.winpcap.org/

使用方法

 1. 記録する

  1.1. 使用するネットワークデバイスを選択する

   個々の環境により変わるので適切なものを選択

  1.2. キャプチャするIPアドレスを選択する

   個々の環境により変わるので適切なものを選択

  1.3. 動作モード(※1)を選択する

  1.4. 開始ボタンを押す

   相手G名取得のタイミングがまちまちなのでできればフィールド転送前に開始していたほうがいい

  1.5. 再度開始ボタンを押す（終了する）

   [ギルド戦_yyyy-mm-dd_HH-MM.log]が出力される

 2. 記録したデータを閲覧・編集する

  2.1. 過去データ読込ボタンを押す

  2.2. 1.で出力したファイル(ギルド戦_yyyy-mm-dd_HH-MM.log)を選択する

  2.3. 統計画面でコメントを記入する

  2.4. 統計情報保存ボタンを押す

   好きなファイル名で保存

※1 動作モード

 1. on-line

  通常のキャプチャモード

 2. off-line

  汎用パケットキャプチャソフト(WireShark等)で記録したpcap形式のファイルを読み込む
  ※ 2バイト文字を含むパスからは読み込み出来ません(ライブラリの仕様（バグ）の可能性の為修正予定なし)

 3. dump

  *.pcap形式のファイルを出力する（保存後off-lineモードで閲覧可能）
  ※ 2バイト文字を含むパスには保存出来ません(ライブラリの仕様（バグ）の可能性の為修正予定なし)

  実質開発時のデバッグ用なので通常は使う機会は無い


TODO

 ・たまに取得漏れがある
   →データが揃えば検証、修正予定

 ・時系列チャートのX軸間隔がチャートとしておかしい
   →開始時刻からの秒or分表記でなら正しい間隔で出力できるが、APIの仕様上時間をグラフの軸として扱えないため時刻での表示は不可能となる
   →とりあえず現状のまま