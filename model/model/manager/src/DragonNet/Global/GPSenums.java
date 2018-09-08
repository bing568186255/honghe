package DragonNet.Global;

import java.util.HashMap;

public enum GPSenums {
	
	hhz_lxs("红河州", "1", "103.381558", "23.372636"),
	hhz_jd("红河州", "2", "103.377621", "23.388072"),
	hhz_cy("红河州", "3", "103.340222", "23.381264"),
	
	gjs_lxs("个旧市", "1", "103.157605", "23.347158"),
	gjs_jd("个旧市", "2", "103.164504","23.391744"),
	gjs_cy("个旧市", "3", "103.127709","23.361756"),
	
	mzx_lxs("蒙自市", "1", "103.351423","23.425956"),
	mzx_jd("蒙自市", "2", "103.40719","23.402079"),
	mzx_cy("蒙自市", "3", "103.351423","23.364397"),
	
	pbmz_lxs("屏边苗族自治县", "1", "103.642267","23.021925"),
	pbmz_jd("屏边苗族自治县", "2", "103.734254","22.999571"),
	pbmz_cy("屏边苗族自治县", "3", "103.666414","22.967099"),
	
	jsx_lxs("建水县", "1", "102.832867","23.618691"),
	jsx_jd("建水县", "2", "102.797222","23.63882"),
	jsx_cy("建水县", "3", "102.855864","23.666891"),
	
	spx_lxs("石屏县", "1", "102.508768","23.689768"),
	spx_jd("石屏县", "2", "102.449552","23.714122"),
	spx_cy("石屏县", "3", "102.485197","23.754877"),
	
	ml_lxs("弥勒市", "1", "103.404244","24.379546"),
	ml_jd("弥勒市", "2", "103.354226","24.436407"),
	ml_cy("弥勒市", "3", "103.453111","24.440618"),
	
	yy_lxs("元阳县", "1", "102.824181","23.265512"),
	yy_jd("元阳县", "2", "102.79601","23.185807"),
	yy_cy("元阳县", "3", "102.889146","23.164013"),
	
	hh_lxs("红河县", "1", "102.427134","23.375774"),
	hh_jd("红河县", "2", "102.234722","23.315616"),
	hh_cy("红河县", "3", "102.420017","23.372926"),
	
	ky_lxs("开远市", "1", "103.288497","23.752195"),
	ky_jd("开远市", "2", "103.198235","23.706673"),
	ky_cy("开远市", "3", "103.30402","23.689202"),
	
	lx_lxs("泸西县", "1", "103.759299","24.581682"),
	lx_jd("泸西县", "2", "103.696633","24.512789"),
	lx_cy("泸西县", "3", "103.827139","24.506476"),
	
	lc_lxs("绿春县", "1", "102.388718","23.050721"),
	lc_jd("绿春县", "2", "102.308805","22.959174"),
	lc_cy("绿春县", "3", "102.450809","22.945863"),
	
	hc_lxs("河口瑶族自治县", "1", "103.925899","22.587263"),
	hc_jd("河口瑶族自治县", "2", "103.841961","22.505018"),
	hc_cy("河口瑶族自治县", "3", "104.006962","22.530124"),
	
	jp_lxs("金平苗族瑶族傣族自治县", "1", "103.242271","22.73719"),
	jp_jd("金平苗族瑶族傣族自治县", "2", "103.11349","22.916264"),
	jp_cy("金平苗族瑶族傣族自治县", "3", "103.260668","22.946086"),
	;

	private GPSenums(String mc, String type, String longitude, String latitude) {
		this.mc = mc;
		this.type = type;
		this.longitude = longitude;
		this.latitude = latitude;

	}

	private String mc;

	private String type;

	private String longitude;

	private String latitude;

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap getMap(String mc,String type){
		HashMap map = new HashMap<>();
		for (GPSenums e : GPSenums.values()) {
			if(mc.equals(e.getMc()) && type.equals(e.getType())){
				map.put("longitude", e.getLongitude());
				map.put("latitude", e.getLatitude());
				return  map;
			}
		}
		return null;
	}

}
