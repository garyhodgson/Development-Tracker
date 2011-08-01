package enums;

enum License {
	
	Unknown("Unknown", "License Unknown", ""),
	PD("Public Domain", "Public Domain", ""),
	None("None", "All Rights Reserved", ""),
	GPLv3("GNU GPL v3", "The GNU General Public License v3", "http://www.gnu.org/licenses/gpl.html"),
	GPLv2("GNU GPL v2", "The GNU General Public License v2", "http://www.gnu.org/licenses/old-licenses/gpl-2.0.html"),
	GPLv1("GNU GPL v1", "The GNU General Public License v1", "http://www.gnu.org/licenses/old-licenses/gpl-1.0.html"),
	LGPLv3("GNU LGPL v3", "The GNU Lesser General Public License v3", "http://www.gnu.org/licenses/lgpl.html"),
	LGPLv2_1("GNU LGPL v2.1", "The GNU Lesser General Public License v2.1", "http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html"),
	AGPL("GNU AGPL", "The GNU Affero General Public License", "http://www.gnu.org/licenses/agpl.html"),
	FDL("GNU FDL", "The GNU Free Documentation License", "http://www.gnu.org/licenses/fdl.html"),
	BSD3("BSD 3 Clause", "The BSD 3-Clause License", "http://www.opensource.org/licenses/BSD-3-Clause"),
	BSD2("BSD 2 Clause", "The BSD 2-Clause License", "http://www.opensource.org/licenses/BSD-2-Clause"),
	MIT("MIT", "The MIT License (MIT)", "http://www.opensource.org/licenses/MIT"),
	Apache("Apache v2", "Apache License, Version 2.0", "http://www.apache.org/licenses/LICENSE-2.0"),
	CCBY("CC BY", "Creative Commons Attribution", "http://creativecommons.org/licenses/by/3.0/"),
	CCBYSA("CC BY-SA", "Creative Commons Attribution-ShareAlike", "http://creativecommons.org/licenses/by-sa/3.0"),
	CCBYND("CC BY-ND", "Creative Commons Attribution-NoDerivs", "http://creativecommons.org/licenses/by-nd/3.0"),
	CCBYNC("CC BY-NC", "Creative Commons Attribution-NonCommercial", "http://creativecommons.org/licenses/by-nc/3.0"),
	CCBYNCSA("CC BY-NC-SA", "Creative Commons Attribution-NonCommercial-ShareAlike", "http://creativecommons.org/licenses/by-nc-sa/3.0"),
	CCBYNCND("CC BY-NC-ND", "Creative Commons Attribution-NonCommercial-NoDerivs", "http://creativecommons.org/licenses/by-nc-nd/3.0"),
	Other("Other", "Other", "");
	
	
	protected String title = null;
	protected String description = null;
	protected String url = null;
	
	License(String title, String description, String url){
		this.title = title;
		this.url = url;
		this.description = description;
	}
	
	public String toString(){
		return (title != null) ? title : this.name();
	}
}
