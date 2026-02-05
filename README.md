# FaceShoots AI Gateway (Simple) — Spring Boot + Java 21

Minimal backend proxy to call **OpenAI gpt-image-1**.
Frontend sends only **image + styleKey**. Backend returns only **images**.

## Requirements
- Java 21
- Maven 3.9+

## Run
```bash
export OPENAI_API_KEY="sk-..."
mvn spring-boot:run
```

## API
### POST /api/generate
multipart/form-data:
- image: jpg/png/webp
- styleKey: doctor | corporate | lawyer

Response:
```json
{ "images": ["data:image/png;base64,...", "..."] }
```

## Curl test
```bash
curl -X POST "http://localhost:8080/api/generate" \
  -F "image=@/path/to/photo.jpg" \
  -F "styleKey=corporate"
```

## Vite proxy (optional)
```js
export default {
  server: { proxy: { "/api": "http://localhost:8080" } }
};
```
