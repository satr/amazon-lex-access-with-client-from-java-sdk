package io.github.satr.aws.lex;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lexruntime.AmazonLexRuntime;
import com.amazonaws.services.lexruntime.AmazonLexRuntimeClientBuilder;
import com.amazonaws.services.lexruntime.model.PostTextRequest;
import com.amazonaws.services.lexruntime.model.PostTextResult;

import java.util.Scanner;

import static org.apache.http.util.TextUtils.isEmpty;

public class Main {

    public static void main(String[] args) {
        AmazonLexRuntime client = AmazonLexRuntimeClientBuilder.standard()
                .withRegion(Regions.US_EAST_1).build();
        PostTextRequest textRequest = new PostTextRequest();
        textRequest.setBotName("TestBotForRequest");
        textRequest.setBotAlias("testbotforrequest");
        textRequest.setUserId("testuser");
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String requestText = scanner.nextLine().trim();
            if(isEmpty(requestText))
                break;

            textRequest.setInputText(requestText);
            PostTextResult textResult = client.postText(textRequest);
            if(textResult.getDialogState().startsWith("Elicit"))
                System.out.println(textResult.getMessage());
            else if(textResult.getDialogState().equals("ReadyForFulfillment"))
                System.out.println(String.format("%s: %s", textResult.getIntentName(), textResult.getSlots()));
            else
                System.out.println(textResult.toString());

        }
        System.out.println("Bye.");
    }
}
