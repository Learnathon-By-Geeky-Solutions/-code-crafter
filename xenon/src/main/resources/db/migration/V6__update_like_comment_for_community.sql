-- Add indexes to improve like and comment retrieval performance
CREATE INDEX IF NOT EXISTS idx_like_blog_id ON blog_like (blog_id);
CREATE INDEX IF NOT EXISTS idx_like_user_id ON blog_like (user_id);
CREATE INDEX IF NOT EXISTS idx_comment_blog_id ON comment (blog_id);
CREATE INDEX IF NOT EXISTS idx_comment_user_id ON comment (user_id);

-- Add created_at column to blog_like table if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns
                   WHERE table_name = 'blog_like' AND column_name = 'created_at') THEN
ALTER TABLE blog_like ADD COLUMN created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP;
END IF;
END$$;

-- Update all existing likes with a creation timestamp if the column was just added
UPDATE blog_like SET created_at = CURRENT_TIMESTAMP WHERE created_at IS NULL;

-- Add display name column to comment table for performance optimization
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns
                   WHERE table_name = 'comment' AND column_name = 'user_display_name') THEN
ALTER TABLE comment ADD COLUMN user_display_name VARCHAR(100);
END IF;
END$$;

-- Update all existing comments with user display name
UPDATE comment c
SET user_display_name = CONCAT(u.first_name, ' ', u.last_name)
    FROM table_user u
WHERE c.user_id = u.id AND c.user_display_name IS NULL;