import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
// import javax.activation.*;
public class corona {
        public static void main(String[] args) {
            while(true) {
                try {

                    URL url = new URL("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict?district_id=730&date=01-05-2021");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();

                    //Getting the response code
                    int responsecode = conn.getResponseCode();

                    if (responsecode != 200) {
                        throw new RuntimeException("HttpResponseCode: " + responsecode);
                    } else {

                        String inline = "";
                        Scanner scanner = new Scanner(url.openStream());

                        //Write all the JSON data into a string using a scanner
                        while (scanner.hasNext()) {
                            inline += scanner.nextLine();
                        }
                        // System.out.println(inline);
                        JSONObject jsonObject = new JSONObject(inline);
                        JSONArray centers = jsonObject.getJSONArray("centers");
                        // System.out.println(centers);
                        for (int i = 0; i < centers.length(); i++) {
                            JSONObject covid = centers.getJSONObject(i);
                            if (covid.get("name").equals("NKDA VC Terminus Building")) {
                                JSONArray NKDA = covid.getJSONArray("sessions");
                                for (int j = 0; j < NKDA.length(); j++) {
                                    JSONObject sess = NKDA.getJSONObject(j);
                                    // System.out.println(session);
                                    if (!sess.get("available_capacity").equals(0)) {
                                        String to = "suryanshbond007@gmail.com";
                                        String from = "suryanshbond007@gmail.com";
                                        String host = "smtp.gmail.com";
                                        Properties properties = System.getProperties();
                                        properties.put("mail.smtp.host", host);
                                        properties.put("mail.smtp.port", "465");
                                        properties.put("mail.smtp.ssl.enable", "true");
                                        properties.put("mail.smtp.auth", "true");
                                        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                                            protected PasswordAuthentication getPasswordAuthentication() {
                                                return new PasswordAuthentication("suryanshbond007@gmail.com", "pass");
                                            }
                                        });
                                        // session.setDebug(true);
                                        try {
                                            MimeMessage message = new MimeMessage(session);
                                            message.setFrom(new InternetAddress(from));
                                            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                                            message.addRecipient(Message.RecipientType.TO, new InternetAddress("kars007bond@gmail.com"));
                                            message.setSubject("COWIN Update");
                                            message.setText("Available now");
                                            System.out.println("sending...");
                                            Transport.send(message);
                                            System.out.println("Sent email successfully....");
                                        } catch (MessagingException mex) {
                                            mex.printStackTrace();
                                        } finally {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        // ((ArrayList)new JSONObject(inline).map.get("centers")).stream().filter( object -> ((JSONObject)object).map.get("name").toString().equals("NKDA VC Terminus Building"))
                        scanner.close();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("hello");
                    Thread.sleep(30 * 60 * 1000);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

}

