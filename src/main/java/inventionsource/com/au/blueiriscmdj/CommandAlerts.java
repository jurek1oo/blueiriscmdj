package inventionsource.com.au.blueiriscmdj;

import com.google.gson.JsonElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class CommandAlerts {

    private static final Logger log = (Logger)LogManager.getLogger(CommandAlerts.class);

    private BlueLogin _blueLogin = null;

    public BlueLogin getBlueLogin() { return _blueLogin; }

    public CommandAlerts(BlueLogin blueLogin) throws Exception {
        _blueLogin = blueLogin;
    }

    public BlueAlerts GetAlertsList(String camera, String dateStart) throws Exception {
        log.debug("camera: " + camera + " dateStart: " + dateStart );
        // return json data element with all alerts details
        // for camera short name or index, 4 all and start date/time
        if(camera==null || camera.length()==0) camera = "index";
        if(dateStart==null || dateStart.length()==0) dateStart = "1970-01-01";
        long secondsFrom1970 =  Utils.GetSecondsFromDateSql(dateStart); //seconds since January 1, 1970
        String cmd = "alertlist";
        String cmdParams = ",\"camera\":\"" + camera + "\",\"startdate\":" + secondsFrom1970;
        JsonElement jsonDataElement = null;
        try {
            boolean hasToBeSuccess = true;
            boolean getDataElement = true;
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);
            jsonDataElement = commandCoreRequest.RunTheCmd(cmd, cmdParams,hasToBeSuccess,getDataElement);

            BlueAlerts alerts = new BlueAlerts(jsonDataElement);
            log.debug("Alerts: " + alerts.toString());
            return alerts;
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n", e);
            throw e;
        }
    }

    public void AlertsDelete() throws Exception {
        log.debug("AlertsDelete");
        String cmd = "alertlist";
        String cmdParams = ",\"camera\":\"index\",\"startdate\":0,\"reset\":true" ;
        try {
            boolean hasToBeSuccess = true;
            boolean getDataElement = false;
            CommandCoreRequest commandCoreRequest = new CommandCoreRequest(_blueLogin);
            commandCoreRequest.RunTheCmd(cmd, cmdParams,hasToBeSuccess,getDataElement);
        } catch (Exception e) {
            log.error("Error executing command: " + cmd + " for BlueIris\n", e);
            throw e;
        }
    }
}