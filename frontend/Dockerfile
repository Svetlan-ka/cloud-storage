FROM node:18-alpine
WORKDIR /app
EXPOSE 8085
COPY package.json package-lock.json ./
RUN npm install
COPY . ./
CMD [ "npm", "run", "serve" ]