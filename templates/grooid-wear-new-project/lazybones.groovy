import static org.apache.commons.io.FilenameUtils.concat
import static org.apache.commons.io.FileUtils.moveFile

// --------------------------------------------
// --------------- DEFAULTS -------------------
// --------------------------------------------

def props = [projectName: projectDir.name]

// --------------------------------------------
// --------------- QUESTIONS ------------------
// --------------------------------------------

props.defaultPackage    = ask('DEFAULT source code package ? [grooid.app]: ', 'grooid.app', 'defaultPackage')
props.minSdkVersion     = ask('MIN version of SDK you want to target ? [19]: ', '19', 'minSdkVersion')
props.targetSdkVersion  = ask('MAX/TARGET version of SDK you want to target ? [25]: ', '21', 'targetSdkVersion')
props.buildToolsVersion = ask('DEFAULT version for Android Build Tools ? [24.0.1]: ', '24.0.1', 'buildToolsVersion')
props.androidSupportV4  = ask('DEFAULT version for Android support v4 ? [24.1.0]: ', '24.1.0', 'androidSupportV4')
props.androidSupportV13  = ask('DEFAULT version for Android support v13 ? [24.1.0]: ', '24.1.0', 'androidSupportV13')
props.grooidVersion  = ask('DEFAULT version for Grooid Android ? [2.4.7]: ', '2.4.7', 'grooidVersion')

// --------------------------------------------
// ----------- PROCESSING TEMPLATES -----------
// --------------------------------------------

processTemplates 'README.md', props
processTemplates '**/build.gradle/**', props
processTemplates '**/strings.xml', props
processTemplates '**/AndroidManifest.xml', props
processTemplates '**/layout/**.xml',props

// -------------------------------------------------
// --- PROCESSING MOBILE GROOVY MAIN TEMPLATES -----
// -------------------------------------------------

def defaultBaseCodePath         = new File("${projectDir}${File.separator}mobile", 'code')
def groovyCodePackagePath       = props.defaultPackage.replace('.' as char, '/' as char)

def groovyCodeTemplatesPath     = new File(defaultBaseCodePath, 'main')
def groovyCodeBasePath          = 'src/main/groovy'
def groovyCodeDestinationPath   = new File("${projectDir}${File.separator}mobile", concat(groovyCodeBasePath, groovyCodePackagePath))

processCode(groovyCodeTemplatesPath, groovyCodeDestinationPath, props)

// -------------------------------------------------
// - PROCESSING MOBILE GROOVY TEST TEMPLATES -------
// -------------------------------------------------

def groovyCodeTestTemplatesPath     = new File("${projectDir}${File.separator}mobile", 'test')
def groovyCodeTestBasePath          = 'src/androidTest/groovy'
def groovyCodeTestDestinationPath   = new File("${projectDir}${File.separator}mobile", concat(groovyCodeTestBasePath, groovyCodePackagePath))

processCode(groovyCodeTestTemplatesPath, groovyCodeTestDestinationPath, props)

// delete 'code' directory
defaultBaseCodePath.deleteOnExit()

// -------------------------------------------------
// --- PROCESSING WEAR GROOVY MAIN TEMPLATES -------
// -------------------------------------------------

def defaultWearBaseCodePath         = new File("${projectDir}${File.separator}wear${File.separator}", 'code')
def groovyWearCodePackagePath       = props.defaultPackage.replace('.' as char, '/' as char)

def groovyWearCodeTemplatesPath     = new File(defaultWearBaseCodePath, 'main')
def groovyWearCodeBasePath          = 'src/main/groovy'
def groovyWearCodeDestinationPath   = new File("${projectDir}${File.separator}wear${File.separator}", concat(groovyWearCodeBasePath, groovyWearCodePackagePath))

processCode(groovyWearCodeTemplatesPath, groovyWearCodeDestinationPath, props)

// -------------------------------------------------
// --- PROCESSING WEAR GROOVY TEST TEMPLATES -------
// -------------------------------------------------

def groovyWearCodeTestTemplatesPath     = new File("${projectDir}${File.separator}wear${File.separator}", 'test')
def groovyWearCodeTestBasePath          = 'src/androidTest/groovy'
def groovyWearCodeTestDestinationPath   = new File("${projectDir}${File.separator}wear${File.separator}", concat(groovyCodeTestBasePath, groovyCodePackagePath))

processCode(groovyWearCodeTestTemplatesPath, groovyWearCodeTestDestinationPath, props)

// delete 'code' directory
defaultWearBaseCodePath.deleteOnExit()
/**
 * Process source file templates at fromDir and moves them at
 * toDir. Then deletes source file templates.
 *
 * @param fromDir where the templates are located
 * @param toDir final destination
 **/
void processCode(File fromDir, File toDir, Map projectProperties) {
    fromDir.listFiles().each { File file ->
        // Processing each template
        processTemplates "**/${file.name}", projectProperties

        // Moving groovy file to the right place
        def sourceName = file.name.replace('gtpl','groovy')
        def destination = new File(toDir, sourceName)

        moveFile(file, destination)
    }

    fromDir.delete()
}
