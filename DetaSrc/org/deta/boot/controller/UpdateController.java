package org.deta.boot.controller;
import java.util.Map;
import mapProcessor.VtoV;
import org.lyg.stable.StableData;
import org.lyg.vpc.process.portImpl.RestDBUpdateImpl;
public class UpdateController {
	public static String exec(String string, Map<String, String> data)
			throws Exception {
		if(string.equalsIgnoreCase(StableData.REST_PATH_UPDATE_ROW_BY_TABLE_PATH_AND_INDEX)){
			return VtoV.ObjectToJsonString(RestDBUpdateImpl
					.updateRowByTablePathAndIndex(data.get("tablePath")
							, data.get("pageIndex"), data.get("culumnOfUpdateRow")
							, data.get("token"), data.get("email")
							, data.get("password"), data.get("auth")));
		}
		return StableData.STRING_EMPTY;
	}
}