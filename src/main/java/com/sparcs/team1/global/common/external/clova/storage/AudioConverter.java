package com.sparcs.team1.global.common.external.clova.storage;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

@Component
public class AudioConverter {

    public File convertAudioToMp3(MultipartFile multipartFile) {
        File tempFile = null;
        File tempSourceFile = null;

        try {
            // MultipartFile을 File로 변환
            tempSourceFile = File.createTempFile("source_audio", ".tmp");
            multipartFile.transferTo(tempSourceFile);

            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(128000);
            audio.setChannels(2);
            audio.setSamplingRate(44100);

            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("mp3");
            attrs.setAudioAttributes(audio);

            Encoder encoder = new Encoder();

            tempFile = File.createTempFile("converted_audio", ".mp3");
            encoder.encode(new MultimediaObject(tempSourceFile), tempFile, attrs);
        } catch (EncoderException | IOException e) {
            e.printStackTrace();
        } finally {
            if (tempSourceFile != null && tempSourceFile.exists()) {
                tempSourceFile.delete();
            }
        }

        return tempFile;
    }
}