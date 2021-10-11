# /bin/bash

get_extra_args() {
	# Determine extra args/flags
	BRANCH="$(git rev-parse --abbrev-ref HEAD)"
	EXTRA_ARGS=""
	
	if [[ "$BRANCH" == "master" ]]; then
		if  [[ ! -z "$BUILD_NUMBER" ]]; then
			EXTRA_ARGS="$EXTRA_ARGS -Drevision=.$BUILD_NUMBER"
		fi
	fi
	
	echo $EXTRA_ARGS
}
