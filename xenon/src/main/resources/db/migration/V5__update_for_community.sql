-- Alter the blog table to add new columns for community enhancements
ALTER TABLE blog
    ADD COLUMN doctor_category VARCHAR(30),
    ADD COLUMN view_count INTEGER DEFAULT 0,
    ADD COLUMN is_featured BOOLEAN DEFAULT FALSE;

-- Increase content length for longer articles
ALTER TABLE blog
ALTER COLUMN content TYPE VARCHAR(3000);

-- Create index for faster queries on category and doctor_category
CREATE INDEX idx_blog_category ON blog (category);
CREATE INDEX idx_blog_doctor_category ON blog (doctor_category);
CREATE INDEX idx_blog_featured ON blog (is_featured);

-- Create index for faster trending queries
CREATE INDEX idx_comment_created_at ON comment (created_at);
CREATE INDEX idx_blog_view_count ON blog (view_count DESC);

-- Create indexes for faster search
CREATE INDEX idx_blog_title_content ON blog (title, content);

-- Update existing blogs to have default view_count and is_featured values
UPDATE blog SET view_count = 0 WHERE view_count IS NULL;
UPDATE blog SET is_featured = FALSE WHERE is_featured IS NULL;