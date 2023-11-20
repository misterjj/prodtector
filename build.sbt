import org.scalajs.linker.interface.ModuleSplitStyle

ThisBuild / scalaVersion := "3.3.1"

lazy val root = project.in(file(".")).
  aggregate(prodtector.js, prodtector.jvm).
  settings(
    publish := {},
    publishLocal := {},
  )

lazy val prodtector = crossProject(JSPlatform, JVMPlatform).in(file(".")).
  settings(
    name := "prodtector",
    version := "0.1-SNAPSHOT",
    libraryDependencies += "com.lihaoyi" %%% "upickle" % "3.1.3"
  ).
  jvmSettings(
    // Add JVM-specific settings here
  ).
  jsSettings(
    // Add JS-specific settings her
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
        .withModuleSplitStyle(ModuleSplitStyle.SmallModulesFor(List("prodtector")))
    },
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.2.0",
    libraryDependencies += "com.raquo" %%% "laminar" % "16.0.0",
    scalaJSUseMainModuleInitializer := true,
  )