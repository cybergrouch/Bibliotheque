#/bin/sh

# from the dropdown at the top of Cloud Console:
export GCLOUD_PROJECT="bibliotheque-418120"
# from Step 2.2 above:
export REPO="app"
# the region you chose in Step 2.4:
export REGION="us-west1"
# whatever you want to call this image:
export IMAGE="latest"

# use the region you chose above here in the URL:
export IMAGE_TAG=${REGION}-docker.pkg.dev/$GCLOUD_PROJECT/$REPO/$IMAGE

gcloud auth configure-docker ${REGION}-docker.pkg.dev

# Build the image:
docker build -t $IMAGE_TAG .
# Push it to Artifact Registry:
docker push $IMAGE_TAG