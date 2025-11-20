FROM node:20 AS build

WORKDIR /app

COPY . .

RUN npm install && npm run build --prod

FROM nginx:1.27.4-alpine

RUN rm -rf /usr/share/nginx/html/*

COPY --from=build /app/dist/consulta-creditos-web /usr/share/nginx/html

COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
