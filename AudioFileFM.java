import java.io.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;


public class AudioFileFM {

    public static void main(String[] args){
        Scanner x=new Scanner(System.in);
        try{
            System.out.print("Enter the filepath you wish to differ only WAV OR OGGS ONLY.:");
      
       String filepath=x.nextLine();
       File file=new File(filepath);
       BufferedInputStream buffered=new BufferedInputStream(new FileInputStream(file));
        
     
       if(WavFile(file)){ // 
           System.out.println("This file is an .WAV");
           BufferedInputStream bufferedinput=new BufferedInputStream(new FileInputStream(file));
           byte[]chunkID=new byte[4];
           byte[]chunk_sizebytes=new byte[4];
           byte[]format_bytes=new byte[4];
           byte[]subchunk1ID_bytes=new byte[4];  // registering file format info as their bytes 
           byte[]subchunk1Size_Bytes=new byte[4];
           byte[]audio_format=new byte[2];
           byte[]no_of_channels=new byte[2];
           byte[]sample_rate=new byte[4];
           byte[] ByteRate=new byte[4];
           byte[]BlockAlign=new byte[2];
           byte[]bitspersample=new byte[2];
           byte[]subchunk2ID=new byte[4];
           byte[]subchunk2Size=new byte[4];
           
           
           bufferedinput.read(chunkID);
           bufferedinput.read(chunk_sizebytes);
           bufferedinput.read(format_bytes);
           bufferedinput.read(subchunk1ID_bytes);
           bufferedinput.read(subchunk1Size_Bytes);
           bufferedinput.read(audio_format);
           bufferedinput.read(no_of_channels);
           bufferedinput.read(sample_rate);
           bufferedinput.read(ByteRate);          // buffer reading the file.
           bufferedinput.read(BlockAlign);
           bufferedinput.read(bitspersample);
           bufferedinput.read(subchunk2ID);
           bufferedinput.read(subchunk2Size);
          
           String chunk_ID=new String(chunkID);
           String fmt=new String(format_bytes);
           System.out.println("chunkID:"+chunk_ID);
           int chunksize=ByteBuffer.wrap(chunk_sizebytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
           String subchunk1ID=new String(subchunk1ID_bytes); //extracting each 4-2 bytes from  the .wav file and using bufferedinputstream and byte buffer to read to the file format of the file.
           int subchunk1size=ByteBuffer.wrap(subchunk1Size_Bytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
           int audioFormat=ByteBuffer.wrap(audio_format).order(ByteOrder.LITTLE_ENDIAN).getShort();
           int numOfChannels=ByteBuffer.wrap(no_of_channels).order(ByteOrder.LITTLE_ENDIAN).getShort();
           int samplerate=ByteBuffer.wrap(sample_rate).order(ByteOrder.LITTLE_ENDIAN).getInt();
           int Byte_Rate=ByteBuffer.wrap(ByteRate).order(ByteOrder.LITTLE_ENDIAN).getInt();  //extracting information from each byte in the following header format
          short Block_Align=ByteBuffer.wrap(BlockAlign).order(ByteOrder.LITTLE_ENDIAN).getShort();
           short  bits_per_sample=ByteBuffer.wrap(bitspersample).order(ByteOrder.LITTLE_ENDIAN).getShort();
           String subchunk2_ID=new String(subchunk2ID);
           int subchunk2_size=ByteBuffer.wrap(subchunk2Size).order(ByteOrder.LITTLE_ENDIAN).getInt();
           
           System.out.println("ChunkSize:"+chunksize);
           System.out.println("Format Type:"+fmt);
           System.out.println("Subchunk1ID:"+subchunk1ID);  //output
           System.out.println("Subchunk1Size:"+subchunk1size);
           System.out.println("Audio Format:"+audioFormat);                          /// printing the following output of the file
           System.out.println("Number of Channels:"+numOfChannels+" Channel/s");
           System.out.println("Sample Rate:"+samplerate+" HZ");
           System.out.println("ByteRate:"+Byte_Rate);
           System.out.println("BlockAlign:"+Block_Align);
           System.out.println("Bits Per Sample:"+bits_per_sample+" bits");
           System.out.println("Subchunk2ID:"+subchunk2_ID);
           System.out.println("Subchunk2size:"+subchunk2_size);
           
     
  
           
       }
       else if(OGGFile(file)){
            System.out.println("This file is an OGG File");
            BufferedInputStream bufferedreader=new BufferedInputStream(new FileInputStream(file));
        byte[]capture_pattern=new byte[4];
        byte[]version_ogg=new byte[1];
         byte[]header_type=new byte[1];
         byte[]granule_position=new byte[8];
         byte[]bitstream_serialno=new byte[4];
         byte[]page_sequence_number=new byte[4];
         byte[]checksum=new byte[4];               // Reading Each 1-4 bytes in OOGS file format and registering them in a byte array
         byte[]page_segment=new byte[1];
         
         
        
        bufferedreader.read(capture_pattern);
        bufferedreader.read(version_ogg);
        bufferedreader.read(header_type);
        bufferedreader.read(granule_position);
        bufferedreader.read(bitstream_serialno); 
        bufferedreader.read(page_sequence_number);
        bufferedreader.read(checksum);
        bufferedreader.read(page_segment);
        
        
        String capturepattern=new String(capture_pattern);
        byte versionogg=version_ogg[0];
        byte headertype=header_type[0];
        long granuleposition=ByteBuffer.wrap(granule_position).order(ByteOrder.LITTLE_ENDIAN).getLong(); //Extracting Each Byte's Info
       int bitstream =ByteBuffer.wrap(bitstream_serialno).order(ByteOrder.LITTLE_ENDIAN).getInt();
       int pagesequence_number=ByteBuffer.wrap(page_sequence_number).order(ByteOrder.LITTLE_ENDIAN).getInt();
       int check_sum=ByteBuffer.wrap(checksum).order(ByteOrder.LITTLE_ENDIAN).getInt();
       int pagesegment=page_segment[0];
         byte[] segment_table=new byte[pagesegment];
         bufferedreader.read(segment_table);
         
       
        
        
           
        
        
           System.out.println("Capture_Pattern Of This File:"+capturepattern);
           System.out.println("Version:"+versionogg);
           System.out.println("Header type of this file:"+headertype);
           System.out.println("Granule Positon:"+granuleposition); // Printing Info
           System.out.println("Bitstream Value:"+bitstream);
           System.out.println("Page Sequence Number:"+pagesequence_number);
            System.out.println("CheckSum CRC32 Value In Hexadecimal:0x"+Integer.toHexString(check_sum)); //printing CheckSUM VALUE IN HEXADECIMAL
            System.out.println("Page Segment Value:"+pagesegment);
            for(int i=0;i<=segment_table.length-1;i++){
                System.out.println("Segment Table"+" "+(i+1)+":"+(segment_table[i]&0xFF)); //printing segment table length values depending on page segments
            }
        
        
          
           
           
       }
       else{
           System.out.println("Unsupported file format."); // if file entered isnt wave neither mp3
       }
    
        x.close(); // closing scanner
       
        }
       
        catch(IOException e){ //exception error if file wasnt inputted correctly
            System.out.println("Error");
        }
    
}
   public static boolean WavFile(File file) {//reading wav header format to differntiate between mp3 and wave
        try {
            BufferedInputStream buffered = new BufferedInputStream(new FileInputStream(file));
            byte[] headerBytes = new byte[44];
            buffered.read(headerBytes);
            String fileHeader = new String(headerBytes);
            buffered.close();
            return fileHeader.startsWith("RIFF");
        } catch (IOException e) {
            System.out.println("Unknown File Format");           // method to detect the wav file format
            return false;
        }
    }

    public static boolean OGGFile(File file) { // reading mp3 header format to differntiate between mp3 and wave
        try {
            BufferedInputStream buffered = new BufferedInputStream(new FileInputStream(file));
   
            byte[] headerBytes = new byte[4];
            buffered.read(headerBytes);
            String fileHeader = new String(headerBytes);
            buffered.close();                                    //method to detect ogg file format
            
            return fileHeader.equals("OggS");
        } catch (IOException e) {
            System.out.println("Unknown File Format.");
            return false;
        }
    }
    
    

}
