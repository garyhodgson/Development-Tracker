package admin

import static development.developmentHelper.*
import development.ThumbnailHelper
import entity.Collaboration
import entity.Development
import enums.Role

def txt = """<?xml version='1.0'?>
<developments count='182' api-version='1'>
   <development id='23008' uri='http://reprap.development-tracker.info/development/23008'>
    <title>1X2</title>
    <created>Tue Aug 23 20:03:42 UTC 2011</created>
    <createdBy>garyhodgson</createdBy>
    <updated>Tue Aug 23 20:07:52 UTC 2011</updated>
    <categories>
      <category>Printer</category>
    </categories>
    <collaborators count='1'>
      <collaborator role='Author'>Mr Kim</collaborator>
    </collaborators>
    <connections count='2'>
      <connection type='Link' url='http://www.thingiverse.com/thing:5773'>Thingiverse Link</connection>
      <connection type='Link' url='http://mrkimrobotics.com/'>mrkimrobotics.com</connection>
    </connections>
    <description>The 1X2 is a Reprap class 3D-printer that can be built with access to only a drill-press and hand-saw. All the parts can be made with one-dimensional tools and straight cuts. Even with these limited requirements, it is designed to be easy to build and have a full set of features.</description>
    <developmentType>Printer</developmentType>
    <goals>
      <goal>Simplification</goal>
    </goals>
    <goalsDescription>The parts for the entire system can be made with one-dimensional tools, straight cuts and common materials.</goalsDescription>
    <license id='GPLv2'>The GNU General Public License v2</license>
    <notice></notice>
    <image url='http://reprap.org/mediawiki/images/thumb/8/89/Mrkim-reprap-1X2-4676.JPG/190px-Mrkim-reprap-1X2-4676.JPG' thumbnail='http://lh5.ggpht.com/65JVAcsbNGTRiNInDxNoFXxJQCP-SvMbn0sxmLz2uh8LeJ_cmFcDhzW44xpX2XttYALhyewjRkgp_INx936sJhuy4c4vAjbF9Q' />
    <projectVendors>
      <projectVendor>RepRap</projectVendor>
    </projectVendors>
    <source url='http://reprap.org/wiki/1X2'>RepRapWiki</source>
    <specifications>
      <specification>
        <name>Material</name>
        <value>Wood</value>
      </specification>
    </specifications>
    <signs />
    <status>Working</status>
    <tags>
      <tag>RepStrap</tag>
    </tags>
  </development>

  <development id='28005' uri='http://reprap.development-tracker.info/development/28005'>
    <title>1X2 Shortcat</title>
    <created>Tue Aug 23 20:13:14 UTC 2011</created>
    <createdBy>garyhodgson</createdBy>
    <updated>Tue Aug 23 20:13:15 UTC 2011</updated>
    <categories>
      <category>Printer</category>
    </categories>
    <collaborators count='1'>
      <collaborator role='Author'>Mr Kim</collaborator>
    </collaborators>
    <connections count='2'>
      <connection type='Link' url='http://www.thingiverse.com/thing:8172'>Thingiverse Link</connection>
      <connection type='Link' url='http://mrkimrobotics.com'>mrkimrobotics.com</connection>
    </connections>
    <description>The SHORTCAT is a 18&amp;Prime; tall, 12&amp;Prime; wide Pyramid framed 3D printer.</description>
    <developmentType>Printer</developmentType>
    <goals>
      <goal>Size</goal>
    </goals>
    <goalsDescription>The SHORTCAT applies TALLCAT style design in a more compact form factor.</goalsDescription>
    <license id='GPLv2'>The GNU General Public License v2</license>
    <notice></notice>
    <image url='http://thingiverse-production.s3.amazonaws.com/renders/3f/2a/d4/1b/32/shortcat-4-5776_preview_medium.jpg' thumbnail='http://lh4.ggpht.com/tgzqv4IrnFOJb563F_vuF7_DXhOSSMnFTg2OY6tt1qX43NpJnHT6SxXMIL7t4V5OXf_1301qGWJt7O4UFhjEPeBvw-b4_Tyx_Q' />
    <projectVendors>
      <projectVendor>RepRap</projectVendor>
    </projectVendors>
    <source url='http://reprap.org/wiki/1X2_Shortcat'>RepRapWiki</source>
    <specifications unit='Metric'>
      <specification>
        <name>Build Envelope</name>
        <value>155mm (W) x 170-190mm (D) x 210-220mm (H)</value>
      </specification>
    </specifications>
    <signs />
    <status>Working</status>
    <tags />
  </development>
  <development id='26003' uri='http://reprap.development-tracker.info/development/26003'>
    <title>1X2 Tallcat</title>
    <created>Tue Aug 23 20:09:26 UTC 2011</created>
    <createdBy>garyhodgson</createdBy>
    <updated>Tue Aug 23 20:09:51 UTC 2011</updated>
    <categories>
      <category>Printer</category>
    </categories>
    <collaborators count='1'>
      <collaborator role='Author'>Mr Kim</collaborator>
    </collaborators>
    <connections count='3'>
      <connection type='Link' url='http://www.thingiverse.com/thing:7317'>Thingiverse Link</connection>
      <connection type='Link' url='http://mrkimrobotics.com'>mrkimrobotics.com</connection>
      <connection type='EvolvedFrom' url='http://reprap.development-tracker.info/development/23008'>1X2 RepRap</connection>
    </connections>
    <description>The 3ft tall 1X2 &amp;quot;TALLCAT&amp;quot; has the enormous vertical build area of 640mm, yet it takes up no more desk space than the &amp;quot;Compact&amp;quot; Reprap 1X2. It has an increased X and Y build area as well.</description>
    <developmentType>Printer</developmentType>
    <goals>
      <goal>Size</goal>
    </goals>
    <goalsDescription />
    <license id='GPLv2'>The GNU General Public License v2</license>
    <notice></notice>
    <image url='http://thingiverse-production.s3.amazonaws.com/renders/c3/05/64/a3/f0/mrkim-tallcat-topview-_5570_preview_medium.jpg' thumbnail='http://lh4.ggpht.com/xZx6lHq4e1bgz5sgjEQfZCQLnmU1zeidI_Rq3y4uvXypA_9Bwf6x9xc4vgN9UPKKY-We0im3FtkztepXuty7LHkVw9xgi_yPM-8' />
    <projectVendors>
      <projectVendor>RepRap</projectVendor>
    </projectVendors>
    <source url='http://reprap.org/wiki/1X2_Tallcat'>RepRapWiki</source>
    <specifications unit='Metric'>
      <specification>
        <name>Build Envelope</name>
        <value>160mm (W) x 190mm (D) x 640mm (H)</value>
      </specification>
    </specifications>
    <signs />
    <status>Working</status>
    <tags />
  </development>
  <development id='148001' uri='http://reprap.development-tracker.info/development/148001'>
    <title>2 Extruder Mod for Prusa Mendel</title>
    <created>Thu Feb 16 08:23:43 UTC 2012</created>
    <createdBy>garyhodgson</createdBy>
    <updated>Thu Feb 16 08:23:47 UTC 2012</updated>
    <categories>
      <category>Extruder</category>
    </categories>
    <collaborators count='1'>
      <collaborator role='Author'>dob71</collaborator>
    </collaborators>
    <connections count='3'>
      <connection type='DesignedFor' url='http://reprap.development-tracker.info/development/7014'>Prusa Mendel</connection>
      <connection type='ConsistsOf' url='http://reprap.development-tracker.info/development/11001'>Greg's Hinged Accessible Extruder</connection>
      <connection type='ConsistsOf' url='http://reprap.development-tracker.info/development/44001'>Lm8uu X Carriage with Fan Mount for Prusa Mendel</connection>
    </connections>
    <description>&amp;quot;This is a 2 extruder mod for Prusa Mendel 3D printer.&amp;quot; - including notes on the electronics and software.</description>
    <developmentType>Modification</developmentType>
    <goals>
      <goal>Improvement</goal>
    </goals>
    <goalsDescription />
    <license id='CCBYSA'>Creative Commons Attribution-ShareAlike</license>
    <notice></notice>
    <image url='http://thingiverse-production.s3.amazonaws.com/renders/69/09/57/2c/7f/IMG_20120125_230821_preview_large.jpg' thumbnail='http://lh5.ggpht.com/wn23ZBbgYh-mWA6YDuuGKxjf-4l93Kx5iTrZHsUdFn4aF97bI98PDySmF-PsOj_OpxeeCeg1-AN742MK3Uz-ufsc2RSXznS4boc' />
    <projectVendors>
      <projectVendor>RepRap</projectVendor>
    </projectVendors>
    <source url='http://www.thingiverse.com/thing:16523'>Thingiverse</source>
    <specifications />
    <signs />
    <status>Working</status>
    <tags>
      <tag>dual-extruder</tag>
    </tags>
  </development>
  <development id='30008' uri='http://reprap.development-tracker.info/development/30008'>
    <title>3 Jaw Z Coupling for Prusa Mendel</title>
    <created>Mon Aug 29 21:30:43 UTC 2011</created>
    <createdBy>garyhodgson</createdBy>
    <updated>Mon Aug 29 21:30:45 UTC 2011</updated>
    <categories>
      <category>Printer</category>
    </categories>
    <collaborators count='1'>
      <collaborator role='Author'>Greg Frost</collaborator>
    </collaborators>
    <connections count='1'>
      <connection type='DesignedFor' url='http://reprap.development-tracker.info/development/7014'>Prusa Mendel</connection>
    </connections>
    <description>This is a new 3-jaw coupling that allows z alignment adjustment.</description>
    <developmentType>Part</developmentType>
    <goals>
      <goal>Accuracy</goal>
      <goal>Reliability</goal>
    </goals>
    <goalsDescription>Print without any Z wobble.</goalsDescription>
    <license id='GPLv2'>The GNU General Public License v2</license>
    <notice></notice>
    <image url='http://thingiverse-production.s3.amazonaws.com/renders/ed/93/bd/20/7c/IMGP0943_preview_medium.jpg' thumbnail='http://lh6.ggpht.com/WBgd0LwBz25XG4FOtKyy8VbCF71KWY7ab5H_FdJ1mVEmVlgwlCMTIU6A3bg5oWVidMcJzVpxjuL44rrkzXZ8YDHGxShHgWdITg' />
    <projectVendors>
      <projectVendor>RepRap</projectVendor>
    </projectVendors>
    <source url='http://www.thingiverse.com/thing:8675'>Thingiverse</source>
    <specifications />
    <signs />
    <status>Working</status>
    <tags />
  </development>
  <development id='24013' uri='http://reprap.development-tracker.info/development/24013'>
    <title>3D Tin</title>
    <created>Wed Aug 24 21:39:29 UTC 2011</created>
    <createdBy>garyhodgson</createdBy>
    <updated>Wed Aug 24 21:39:32 UTC 2011</updated>
    <categories>
      <category>Modelling</category>
      <category>Software</category>
    </categories>
    <description>Online 3D modelling tool.</description>
    <developmentType>Tool</developmentType>
    <goals>
      <goal>Simplification</goal>
      <goal>Fun</goal>
    </goals>
    <goalsDescription />
    <license id='None'>All Rights Reserved</license>
    <notice></notice>
    <image url='Uploaded File: 2011-08-24 23h36_25.png' thumbnail='http://lh6.ggpht.com/G0sWe2wcQQEdbIfxZ-lEezW2idIAr2OvJ2Mv9QrcA4ATSNcVJ8TbF_Y9y8UjXEixSTGjycqJZbhSgH_XT2RsJ_EWI7eNXRTp' />
    <projectVendors>
      <projectVendor>Any</projectVendor>
    </projectVendors>
    <source url='http://www.3dtin.com/'>Other</source>
    <specifications />
    <signs />
    <status>Working</status>
    <tags />
  </development>
  <development id='75003' uri='http://reprap.development-tracker.info/development/75003'>
    <title>3DTouch</title>
    <created>Wed Oct 05 11:18:15 UTC 2011</created>
    <createdBy>garyhodgson</createdBy>
    <updated>Wed Oct 05 11:20:10 UTC 2011</updated>
    <categories>
      <category>Commercial</category>
    </categories>
    <collaborators count='1'>
      <collaborator role='Author'>bitsfrombytes</collaborator>
    </collaborators>
    <description>&amp;quot;3DTouch (TM) Personal color 3D Printer. It is easy to set up and easy to use with a touch screen user interface, and is ideal for desktop 3D printing in the home, classroom and office.&amp;quot;</description>
    <developmentType>Printer</developmentType>
    <goals />
    <goalsDescription />
    <license id='None'>All Rights Reserved</license>
    <notice></notice>
    <image url='http://www.bitsfrombytes.com/sites/www.bitsfrombytes.com/files/imagecache/product/BFB-3DTouch-Smoke-3D-Printer.jpg' thumbnail='http://lh3.ggpht.com/rhySdHHyRxmFtqfG25xPIuajUd8JTdTmK8sv4VhSQsYZlSgX9G3LaCiHVHHcFCMcoT9lOdv9FxoLC_mOsMzZiXJqUv_c7hS1xro' />
    <projectVendors>
      <projectVendor>Bits from Bytes</projectVendor>
    </projectVendors>
    <source url='http://www.bitsfrombytes.com/catalog/3dtouch'>Other</source>
    <specifications unit='Metric'>
      <specification>
        <name>Build Envelope</name>
        <value>275mm (W) x 275mm (D) x 210mm (H)</value>
      </specification>
      <specification>
        <name>Size</name>
        <value>515mm (W) x 515mm (D) x 590mm (H)</value>
      </specification>
      <specification>
        <name>Weight</name>
        <value>38 kg</value>
      </specification>
    </specifications>
    <signs />
    <status>Working</status>
    <tags />
  </development>
  <development id='107001' uri='http://reprap.development-tracker.info/development/107001'>
    <title>555 timer controlled heated print bed</title>
    <created>Mon Nov 21 19:46:43 UTC 2011</created>
    <createdBy>garyhodgson</createdBy>
    <updated>Mon Nov 21 19:46:47 UTC 2011</updated>
    <categories />
    <collaborators count='1'>
      <collaborator role='Author'>jjshortcut</collaborator>
    </collaborators>
    <connections count='1'>
      <connection type='Link' url='http://www.thingiverse.com/thing:13815'>Thingiverse Page</connection>
    </connections>
    <description>PCB heated bed with a standalone 555-timer based controller.</description>
    <developmentType>Part</developmentType>
    <goals>
      <goal>Necessity</goal>
    </goals>
    <goalsDescription />
    <license id='CCBYNC'>Creative Commons Attribution-NonCommercial</license>
    <notice></notice>
    <image url='http://thingiverse-production.s3.amazonaws.com/renders/75/20/f6/ae/08/IMG_1026_Kopie_preview_medium.jpg' thumbnail='http://lh5.ggpht.com/vMcuZeQzi1ZhuLB5qa23O_ja9f2oz88cuC0iqV741pf57BlnpPuVZlb2N1R5qMrzHw6D7kVr0yyqOTVL3u_bD7uibf1Cqm-tmA' />
    <projectVendors>
      <projectVendor>RepRap</projectVendor>
    </projectVendors>
    <source url='http://jjshortcut.wordpress.com/2011/11/21/555-timer-controlled-heated-print-bed/'>Blog</source>
    <specifications unit='Metric'>
      <specification>
        <name>Size</name>
        <value>100mm (W) x 160mm (D)</value>
      </specification>
    </specifications>
    <signs />
    <status>Working</status>
    <tags>
      <tag>Heated Bed</tag>
    </tags>
  </development>
  <development id='32005' uri='http://reprap.development-tracker.info/development/32005'>
    <title>Accessible Wade's Extruder</title>
    <created>Mon Aug 29 20:24:03 UTC 2011</created>
    <createdBy>garyhodgson</createdBy>
    <updated>Mon Aug 29 20:41:00 UTC 2011</updated>
    <categories>
      <category>Extruder</category>
    </categories>
    <collaborators count='1'>
      <collaborator role='Author'>Greg Frost</collaborator>
    </collaborators>
    <connections count='1'>
      <connection type='DerivedFrom' url='http://reprap.development-tracker.info/development/9001'>Wade's Geared Nema 17 Extruder</connection>
    </connections>
    <description>This is an extruder based strongly on the wades from the Prusa git repo. It has several features over the standard wade:
+ Accessible hobbing
+ The motor can be removed without removing the large gear.
+ Slightly higher gear ratio than standard wade. This design uses 43:10 instead of 37:11.</description>
    <developmentType>Part</developmentType>
    <goals>
      <goal>Improvement</goal>
      <goal>Maintainability</goal>
    </goals>
    <goalsDescription />
    <license id='GPLv2'>The GNU General Public License v2</license>
    <notice>Superseded by Greg's Hinged Accessible Extruder.</notice>
    <image url='http://thingiverse-production.s3.amazonaws.com/renders/98/64/71/02/b9/IMGP0766_preview_medium.jpg' thumbnail='http://lh6.ggpht.com/u713sE52pcj-5av3j_Ea_GWSxLqjDwT__AubtdCQPsTNNerbix_ksTd3xnMYHa_PB9EBNsYDwFRC5SyJH55wqwvZ1FxixyYBww' />
    <projectVendors>
      <projectVendor>RepRap</projectVendor>
    </projectVendors>
    <source url='http://www.thingiverse.com/thing:6713'>Thingiverse</source>
    <specifications>
      <specification>
        <name>Gear Ratio</name>
        <value>43:10</value>
      </specification>
    </specifications>
    <signs>
      <sign>Obsolete</sign>
    </signs>
    <status>Obsolete</status>
    <tags />
  </development>
</developments>
"""

def developments = new XmlSlurper().parseText(txt)

def developmentList = developments.development

developmentList.each {
	def d = new Development(title:it.title.text(),createdBy:'test', description:it.description.text(), status:enums.Status.valueOf(it.status.text()), imageURL:it.image.@url.text())
	def developmentKey = dao.ofy().put(d)
	def author = it.'**'.grep{ it.@role == 'Author' }[0]?.text()
	if (author){
		def c = new Collaboration(development:developmentKey, name:author, role:Role.Author)
		dao.ofy().put(c)
	}
	
	(new ThumbnailHelper()).generateThumbnail(null, [imageURL:it.image.@thumbnail.text()], d)
}
cacheManager.resetDevelopmentCache()
