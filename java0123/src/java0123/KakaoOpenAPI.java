package java0123;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class KakaoOpenAPI {

	public static void main(String[] args) {
		//데이터를 다운로드 받는 부분
		String json = null;
		try {
			//검색할 도서이름 입력
			System.out.print("검색할 도서명:");
			Scanner sc = new Scanner(System.in);
			String book = sc.nextLine();
			
			//book을 한글로 입력할 수 도 있으므로 인코딩
			book = URLEncoder.encode(book, "utf-8");
			
			//다운로드 받을 URL생성
			URL url = new URL(
				"https://dapi.kakao.com/v3/search/book?size=50&page=2&sort=latest&target=title&query=" + book);
			//연결 객체 생성
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			//옵션 설정
			con.setConnectTimeout(20000);
			con.setUseCaches(false);
			//헤더 설정
			con.addRequestProperty(
				"Authorization", "KakaoAK 228e2b377f259ec5a956a267b27ded62");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuilder sb = new StringBuilder();
			while(true) {
				String line = br.readLine();
				if(line == null) {
					break;
				}
				sb.append(line + "\n");
			}
			json = sb.toString();
			
			br.close();
			con.disconnect();
			sc.close();
			
			//System.out.println(json);
			
		}catch(Exception e) {
			System.out.println("다운로드 예외:" + e.getMessage());
			e.printStackTrace();
		}
		
		//데이터를 파싱하는 부분
		if(json == null) {
			System.out.println("읽어온 데이터가 없습니다.");
		}else {
			//전체 문자열을 JSON 객체로 변환
			JSONObject root = new JSONObject(json);
			//System.out.println(root);
			
			//meta 키의 내용을 JSONObject로 가져오기
			//JSONObject meta = root.getJSONObject("meta");
			//System.out.println(meta);
			
			//documents 키의 내용을 JSONArray로 가져오기
			JSONArray documents = root.getJSONArray("documents");
			//System.out.println(documents);
			
			//배열의 데이터 개수 찾아오기
			int len = documents.length();
			for(int i=0; i<len; i=i+1) {
				JSONObject document = documents.getJSONObject(i);
				//System.out.println(document);
				try {
					String title = document.getString("title");
					int price = document.getInt("price");
					String thumb = document.getString("thumbnail");
					//인코딩은 문자열을 메모리에 저장되는 코드로 변환하는 것이고 
					//디코딩은 메모리에 저장된 코드를 원래의 문자열로 복원하는 것입니다.
					System.out.println(
							title + ":" + price + "원" + URLDecoder.decode(thumb,"utf-8"));
				}catch(Exception e) {}
			}
		}

	}

}







