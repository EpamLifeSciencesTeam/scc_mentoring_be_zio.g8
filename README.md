A [Giter8][g8] template for EPAM Scala Competency Center ZIO+Doobie+Http4s web application!

# How to use this project

First of all, you'll need [SBT](https://www.scala-sbt.org). Follow its [installation instructions](https://www.scala-sbt.org/download.html) and make sure `sbt console` opens Scala REPL.

Next, you'll need to run `sbt new scc_mentoring_be_zio` and answer its questions about organization name (package name) and project name.

This scaffolded project will contain:
- [ZIO](https://zio.dev)
- [doobie](https://tpolecat.github.io/doobie/)
- [http4s](https://http4s.org)
- [circe](https://circe.github.io/circe/)
- [pureconfig](https://pureconfig.github.io)
- various QoL things like [kind-projector](https://github.com/typelevel/kind-projector) or [better-monadic-for](https://github.com/oleg-py/better-monadic-for)

To run the generated project, you'll want to edit the `src/main/resources/application.conf` file and provide your own connection options to PostgreSQL database.

Execute `sbt run` to start Blaze server on the default 8083 port — or edit `application.conf` and provide your desired port number instead.

### Credits

Thanks to Maxim Schuwalow for [his implementation of ZIO Todo Backend](https://github.com/mschuwalow/zio-todo-backend), which inspired this template.

Template license
----------------
Written in 2021 by Yuriy Bogomolov \<yuriy.bogomolov@gmail.com>

To the extent possible under law, the author(s) have dedicated all copyright and related
and neighboring rights to this template to the public domain worldwide.
This template is distributed without any warranty. See <http://creativecommons.org/publicdomain/zero/1.0/>.

[g8]: http://www.foundweekends.org/giter8/
