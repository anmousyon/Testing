using System;
using System.Collections.Generic;
using RedditSharp;
using MongoDB.Bson;
using MongoDB.Driver;
using System.IO;

namespace ellie
{
	class MainClass
	{
		public static void Main()
		{
            var controller = new Controller();
            controller.buildDatabase();
            controller.displayDatabase();
		}
	}

	public class Controller
	{
		public Database database;

		public Controller()
		{
			database = new Database();
		}

		public void displayDatabase()
		{
			var cleaner = new Cleaner();

			using (var cursor = database.posts.FindSync(new BsonDocument(), new FindOptions<BsonDocument>()))
			{
				while (cursor.MoveNext())
				{
					var batch = cursor.Current;
					foreach (BsonDocument postData in batch)
					{
						var post = new Post();
						post = cleaner.load(postData, post);
						Console.WriteLine(post.label);
					}
				}
			}
            using (var cursor = database.comments.FindSync(new BsonDocument(), new FindOptions<BsonDocument>()))
            {
                while (cursor.MoveNext())
                {
                    var batch = cursor.Current;
                    foreach (BsonDocument commentData in batch)
                    {
                        var comment = new Comment();
                        comment = cleaner.load(commentData, comment);
                        Console.WriteLine(comment.label);
                    }
                }
            }
        }

		public void buildDatabase()
		{
			var reddit = new Reddit();
			var cleaner = new Cleaner();
			foreach (var subreddit in reddit.getSubreddits().GetRange(0,2))
			{
				foreach (var post in reddit.getPosts(subreddit))
				{
					database.insert(cleaner.clean(post), new Post());
					foreach (var comment in reddit.getComments(post))
					{
						database.insert(cleaner.clean(comment), new Comment());
					}
				}
			}
		}
	}

	public class Database
	{
		public IMongoClient client;
		public IMongoCollection<BsonDocument> posts, comments;
		public Database()
		{
			client = new MongoClient();
			Tuple <IMongoCollection<BsonDocument>, IMongoCollection<BsonDocument>> collections = load();
			posts = collections.Item1;
			comments = collections.Item2;
		}

		public Tuple<IMongoCollection<BsonDocument>, IMongoCollection<BsonDocument>> load()
		{
			IMongoDatabase database = client.GetDatabase("submissions");
			return Tuple.Create(
				database.GetCollection<BsonDocument>("Post"),
				database.GetCollection<BsonDocument>("Comment")
			);
		}

		public void insert(BsonDocument post, Post empty)
		{
			posts.InsertOne(post);	
		}

		public void insert(BsonDocument comment, Comment empty)
		{
			comments.InsertOne(comment);
		}
	}

	public class Cleaner
	{
		public Analyzer analyzer;
		public Cleaner()
		{
			analyzer = new Analyzer();
		}

		public BsonDocument clean(RedditSharp.Things.Post post)
		{
			var dirty = new Post(post);
			dirty.TitleSentiment = analyzer.calcSentiment(post.Title);
			return dump(dirty);
		}

		public BsonDocument clean(RedditSharp.Things.Comment comment)
		{
			var dirty = new Comment(comment);
			dirty.BodySentiment = analyzer.calcSentiment(comment.Body);
			return dump(dirty);
		}

		public BsonDocument dump(Post post)
		{
			var document = new BsonDocument
			{
				{ "label", post.label },
				{ "subreddit", post.subreddit },
				{ "username", post.username },
				{ "score", post.score },
				{ "titleSentiment", post.TitleSentiment }
			};
			return document;
		}

		public BsonDocument dump(Comment comment)
		{
			var document = new BsonDocument
			{
				{ "label", comment.label },
				{ "subreddit", comment.subreddit },
				{ "username", comment.username },
				{ "score", comment.score },
				{ "bodySentiment", comment.BodySentiment }
			};
			return document;
		}

		public Post load(BsonDocument post, Post loaded)
		{
			loaded = new Post(
				post["label"].ToString(),
				post["subreddit"].ToString(),
				post["username"].ToString(),
				post["score"].ToString(),
				post["titleSentiment"].ToString()
			);
			return loaded;
		}

		public Comment load(BsonDocument comment, Comment loaded)
		{
			loaded = new Comment(
				comment["label"].ToString(),
				comment["subreddit"].ToString(),
				comment["username"].ToString(),
				comment["score"].ToString(),
				comment["bodySentiment"].ToString()
			);
			return loaded;
		}
	}

	public class Reddit
	{
		private BotWebAgent webagent;
		public RedditSharp.Reddit reddit;

		public Reddit()
		{
			reddit = new RedditSharp.Reddit(login(), false);
		}

		public BotWebAgent login()
		{
            string[] keys = File.ReadAllLines("../../keys.txt");
			var username = keys[0];
			var password = keys[1];
			var id = keys[2];
			var secret = keys[3];
			var uri = keys[4];
			webagent = new BotWebAgent(username, password, id, secret, uri);
			return webagent;
		}

		public List<RedditSharp.Things.Subreddit> getSubreddits()
		{
            string[] subredditNames = File.ReadAllLines("../../subs.txt");
			var subreddits = new List<RedditSharp.Things.Subreddit> { };
			foreach (string name in subredditNames)
			{
                var fullName = "/r/" + name.Trim();
                Console.WriteLine(fullName);
                subreddits.Add(reddit.GetSubreddit(fullName));
			}
			return subreddits;
		}

		public Listing<RedditSharp.Things.Post> getPosts(RedditSharp.Things.Subreddit subreddit)
		{
			return subreddit.Posts;
		}

		public RedditSharp.Things.Comment[] getComments(RedditSharp.Things.Post post)
		{
			return post.Comments;
		}
	}

	public class Analyzer
	{
		public Analyzer()
		{
		}

		public string calcSentiment(string text)
		{
            if (i4Ds.LanguageToolkit.LanguageDetector.DetectLanguage(text) != null)
            {
                return i4Ds.LanguageToolkit.SentimentAnalyzer.GetSentiment(text).ToString();
            }
            else
            {
                return "null";
            }
		}
	}

	public class Submission
	{
		public string label;
		public string subreddit;
		public string username;
		public string score;

		public Submission(RedditSharp.Things.Post item)
		{
			label = item.Id;
			subreddit = item.SubredditName;
			username = item.AuthorName;
			score = item.Score.ToString();
		}
        public Submission()
        {
        }
		public Submission(
			string label,
			string subreddit,
			string username,
			string score
		)
		{
			this.label = label;
			this.subreddit = subreddit;
			this.username = username;
			this.score = score;
		}

		public Submission(RedditSharp.Things.Comment item)
		{
			label = item.Id;
			subreddit = item.Subreddit;
			username = item.Author;
			score = item.Score.ToString();
		}
	}

	public class Post : Submission
	{
		private string titleSentiment;
		public string TitleSentiment
		{
			get
			{
				return titleSentiment;
			}
			set
			{
				titleSentiment = value;
			}
		}
        public Post()
        {
        }
		public Post(
			string label,
			string subreddit,
			string username,
			string score,
			string titleSentiment
		) : base(label, subreddit, username, score)
		{
			TitleSentiment = titleSentiment;
		}
		public Post(RedditSharp.Things.Post post) : base(post)
		{
		}
	}

	public class Comment : Submission
	{
		private string bodySentiment;
		public string BodySentiment
		{
			get
			{
				return bodySentiment;
			}
			set
			{
				bodySentiment = value;
			}
		}
        public Comment()
        {
        }
		public Comment(
			string label,
			string subreddit,
			string username,
			string score,
			string bodySentiment
		) : base(label, subreddit, username, score)
		{
			BodySentiment = bodySentiment;
		}
		public Comment(RedditSharp.Things.Comment comment) : base(comment)
		{
		}
	}
}