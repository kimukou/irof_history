//println("path => " + this.class.protectionDomain.codeSource.location.path)//println("fileName => " + new File(this.class.protectionDomain.codeSource.location.file).name)def loc=this.getClass().getProtectionDomain().getCodeSource().getLocation()println "===start($loc)==="def prop1 = new Properties()prop1.load(new FileInputStream('local.properties'))def config = new ConfigSlurper().parse(prop1)def adb_home="${config.sdk.dir}/platform-tools"def apkName= "${config.release.app.name}_${config.release.app.version}.apk"println apkNamedef manifestXml = new XmlSlurper().parse(new File("AndroidManifest.xml"))//println manifestXml.dump()pkgName = manifestXml.@package.text()println pkgName//def allActivity = manifestXml.application.activity//allActivity.each{//	println it.dump()//}activity = manifestXml.application.activity[0].@'android:name'.text()println activity//exit(0)def ant = new AntBuilder()println ant.dump()//read build.xmlimport org.apache.tools.ant.Projectimport org.apache.tools.ant.ProjectHelperdef antFile = new File("./build.xml")def project = new Project()project.init()ProjectHelper.configureProject(project, antFile);//project.executeTarget(project.defaultTarget);//project.executeTargets(['clean', 'release'] as Vector)project.executeTargets(['clean', 'debug'] as Vector)//cmd = "${adb_home}/adb devices"//println cmd//def proc = cmd.execute()//proc.waitFor()//see http://www.dzeta.jp/~junjis/code_reading/index.php?ant%2F%A5%D3%A5%EB%A5%C9%A5%D7%A5%ED%A5%BB%A5%B9%A4%F2%C6%C9%A4%E0//=== after-copy ==org_file = project.getProperty('out.final.file')to_file="./${project.getProperty('out.dir')}/../${apkName}"println "org_file=$org_file"ant.echo "to_file=$to_file"ant.copy(file:org_file,tofile:to_file,overwrite:true)//=== after-copy-install ==cmd=''ant.exec(outputproperty:"cmdOut",	errorproperty: "cmdErr",	resultproperty:"cmdExit",	failonerror: "false",	dir:".",	executable: "${adb_home}/adb") {		arg(line:"devices")}println "[$cmd]<${ant.project.properties.cmdExit}>=${ant.project.properties.cmdOut}"	def deviceList = []def txt =ant.project.properties.cmdOutprintln txttxt.split('\n').each{	if(""== it.trim() || it=~/List*/ )return;//same continure	device = it.split('\t')[0]	println device	deviceList.add device}println deviceList.dump()cmd=''deviceList.each{	cmd ="-s $it uninstall $pkgName"	ant.exec(outputproperty:"cmdOut2",		errorproperty: "cmdErr2",		resultproperty:"cmdExit2",		dir:".",		failonerror: "false",		executable: "${adb_home}/adb") {			arg(line:cmd)	}	println "[$cmd]<${ant.project.properties.cmdExit2}>=${ant.project.properties.cmdOut2}"	//println "stderr:         ${ant.project.properties.cmdErr2}"	cmd = "-s $it install $apkName"	ant.exec(outputproperty:"cmdOut3",		errorproperty: "cmdErr3",		resultproperty:"cmdExit3",		dir:".",		failonerror: "false",		executable: "${adb_home}/adb") {			arg(line:cmd)	}	println "[$cmd]<${ant.project.properties.cmdExit3}>=${ant.project.properties.cmdOut3}"	//println "stderr:         ${ant.project.properties.cmdErr3}"			cmd = "-s $it shell am start -a android.intent.action.MAIN -n ${pkgName}/${activity}"	ant.exec(outputproperty:"cmdOut3",		errorproperty: "cmdErr4",		resultproperty:"cmdExit4",		dir:".",		failonerror: "false",		executable: "${adb_home}/adb") {			arg(line:cmd)	}	println "[$cmd]<${ant.project.properties.cmdExit4}>=${ant.project.properties.cmdOut4}"	//println "stderr:         ${ant.project.properties.cmdErr4}"}println "===end($loc)==="