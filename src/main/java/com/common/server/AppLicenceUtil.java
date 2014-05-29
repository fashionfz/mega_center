/*
 * �������� 2005-9-1
 */

package com.common.server;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.common.licence.LicenseNotFoundException;
import com.listener.SpringUtil;
import com.service.IDeviceService;
import com.service.IUserService;




/**
 * @author Administrator
 */
public class AppLicenceUtil
{

	private static int licenceMode=1; 

    private static Log log = LogFactory.getLog(AppLicenceUtil.class);


    public static Map getLicence()
        throws Exception
    {

        Map map = new HashMap();
        
        if(licenceMode==1){
        	try
            {
                map = VertifyLicence.getInfo("megaeyes.licence");
            }
            catch (LicenseNotFoundException e)
            {
            	
                e.printStackTrace();
            }
            
        }else if(licenceMode==2){
        	log.info("���汾�����ð汾��");
        	map.put("videoCount","12000000");
        	map.put("userCount","1200000");
        	map.put("expireDate","2015-01-01");
        }
        
        return map;
    }

    public static int videoCount() throws Exception
    {


        Map map = getLicence();
        String videoCount = (String) map.get("videoCount");
        if (videoCount != null)
        {

            int count = Integer.valueOf(videoCount).intValue();
            return count;

        }else
            return 0;
    }
    public static int userCount() throws Exception
    {

        Map map = getLicence();
        String userCount = (String) map.get("userCount");
        if (userCount != null)
        {

            int count = Integer.valueOf(userCount).intValue();
            return count;

        }else
            return 0;
    }

    public static void logonVertify() throws Exception
    {
        Map map = getLicence();
        String expire = (String) map.get("expireDate");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if (expire == null)
        {
            return;
        }
        Date expireDate=null;
		try {
			expireDate = format.parse(expire);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Date currentDate = new Date();
        if (currentDate.compareTo(expireDate) >= 0)
        {
            log.info("��֤��ͨ��:expire=" + expire + "  currentDate=" + format.format(currentDate));

        }
        log.info("��֤ͨ��:expire=" + expire + "  currentDate=" + format.format(currentDate));
    }
}