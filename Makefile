docker-build:
	docker build -t app .

docker-run: docker-build
	docker run -p 80:80 app

docker-scan-image-vulnerabilities:
	docker scout quickview

gcloud-upload:
	deploy.sh

git-push-current-branch:
	git push upstream `git branch --show-current` --force 
