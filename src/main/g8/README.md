# $name;format="normalize"$

This scaffolded project contains:
- [ZIO](https://zio.dev)
- [doobie](https://tpolecat.github.io/doobie/)
- [http4s](https://http4s.org)
- [circe](https://circe.github.io/circe/)
- [pureconfig](https://pureconfig.github.io)
- various QoL things like [kind-projector](https://github.com/typelevel/kind-projector) or [better-monadic-for](https://github.com/oleg-py/better-monadic-for)

To run this project, you'll want to edit the `src/main/resources/application.conf` file and provide your own connection options to PostgreSQL database.

Execute `sbt run` to start Blaze server on the default 8083 port — or edit `application.conf` and provide your desired port number instead.
