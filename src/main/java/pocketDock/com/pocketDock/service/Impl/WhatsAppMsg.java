package pocketDock.com.pocketDock.service.Impl;

import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class WhatsAppMsg {
    public static void sendWhatsAppMessage(String phoneNumber, String message) throws Exception {
        RequestBody body = new FormBody.Builder()
                .add("token", "kya5ly4aeqky6d5h")
                .add("to", phoneNumber) // Utilisez le numéro de téléphone du cuisinier
                .add("body", message)   // Utilisez le message approprié
                .build();

        Request request = new Request.Builder()
                .url("https://api.ultramsg.com/instance83999/messages/chat")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();

        // Vérifiez la réponse
        if (!response.isSuccessful()) {
            throw new IOException("Erreur lors de l'envoi du message WhatsApp: " + response);
        }

        // Assurez-vous de fermer la réponse pour libérer les ressources
        response.close();
    }


}
