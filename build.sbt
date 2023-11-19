import org.scalajs.linker.interface.ModuleSplitStyle

lazy val prodtector = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    scalaVersion := "3.3.1",
    scalacOptions ++= Seq("-encoding", "utf-8", "-deprecation", "-feature"),

    // We have a `main` method
    scalaJSUseMainModuleInitializer := true,

    // Emit modules in the most Vite-friendly way
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
        .withModuleSplitStyle(ModuleSplitStyle.SmallModulesFor(List("prodtector")))
    },

    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.2.0",
    libraryDependencies += "com.raquo" %%% "laminar" % "16.0.0",
    libraryDependencies += "com.lihaoyi" %%% "upickle" % "3.1.3",

//    fastLinkOutputDir := {
//      // Ensure that fastLinkJS has run, then return its output directory
//      (Compile / fastLinkJS).value
//      (Compile / fastLinkJS / scalaJSLinkerOutputDirectory).value.getAbsolutePath()
//    },
//
//    fullLinkOutputDir := {
//      // Ensure that fullLinkJS has run, then return its output directory
//      (Compile / fullLinkJS).value
//      (Compile / fullLinkJS / scalaJSLinkerOutputDirectory).value.getAbsolutePath()
//    },
  )